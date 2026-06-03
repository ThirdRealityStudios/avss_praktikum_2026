package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class JsonSQLService<T> implements IJsonSQLService<T>
{
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private MySQLAdapter mySQLAdapter;

    public JsonSQLService(GsonBuilder gsonBuilder) throws IllegalArgumentException, SQLException
    {
        if(gsonBuilder == null)
            throw new IllegalArgumentException("argument must not be null!");

        gsonBuilder.setPrettyPrinting();
        this.gsonBuilder = gsonBuilder;

        this.gson = gsonBuilder.create();

        mySQLAdapter = new MySQLAdapter("localhost:3306", "lms");

        if(!mySQLAdapter.connect("root", "password"))
            throw new SQLException("Connection failed!");
    }

    private <E> String genTableNameFrom(Class<E> className)
    {
        Objects.requireNonNull(className);

        String classCanonicalName = className.getCanonicalName();

        /**
         * E.g. java.util.HashMap.SimpleEntry.class (nested class)
         *
         * Calling now getCanonicalName() would return:
         *
         * java.util.AbstractMap.SimpleEntry
         *
         * But after genTableNameFrom(..) it is:
         *
         * java_util_AbstractMap_SimpleEntry
         */
        return classCanonicalName.replace('.', '_');
    }

    /**
     * Additionally to genTableNameFrom(..),
     * this also adds the database name before the "canonical" table name.
     * This helps your DB runtime environment to know which database is selected for executions.
     *
     * @param className
     * @return database name + . + "canonical" table name
     * @param <E>
     */
    private <E> String genQualifiedTableNameFrom(Class<E> className)
    {
        return mySQLAdapter.getDatabaseName() + "." + genTableNameFrom(className);
    }

    /**
     * Should be called before any CRUD action in this class
     * which requires corresponding access.
     *
     * @param obj
     * @throws NullPointerException
     * @throws SQLException
     */
    private void ensureTableFor(Object obj) throws NullPointerException, SQLException
    {
        Objects.requireNonNull(obj);

        String tableName = genTableNameFrom(obj.getClass());

        Objects.requireNonNull(tableName);

        if(tableName.isEmpty())
            throw new NullPointerException("Table name cannot be empty (internal error)");

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + tableName + "( ID INT(8) PRIMARY KEY UNIQUE , Json TEXT NOT NULL );";

        try(Statement stmt = mySQLAdapter.getConnection().createStatement())
        {
            stmt.executeUpdate(sqlCreateTable);
            stmt.getConnection().commit();
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }
    }

    @Override
    public boolean save(int id, Object obj)
    {
        Objects.requireNonNull(obj);

        try
        {
            ensureTableFor(obj);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();

            return false;
        }

        String qualifiedTableName = genQualifiedTableNameFrom(obj.getClass());

        String json = gson.toJson(obj);

        // Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
        //
        // Btw: you cannot use question mark placeholder in sql statement for a table name!
        // Only where normal values would be expected.

        // If you see: INSERT + UPDATE fallback logic 👉 replace it with: ON DUPLICATE KEY UPDATE almost every time in MySQL.
        String sqlUpsertJson = "INSERT INTO " + qualifiedTableName + "(ID, Json) VALUES(?, ?) ON DUPLICATE KEY UPDATE Json=VALUES(Json)";

        try(PreparedStatement stmtInsert = mySQLAdapter.getConnection().prepareStatement(sqlUpsertJson))
        {
            stmtInsert.setInt(1, id);
            stmtInsert.setString(2, json);

            mySQLAdapter.getConnection().commit();

            return true;
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }

        try
        {
            mySQLAdapter.getConnection().rollback();
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }

        return false; // error, could not save.
    }

    @Override
    public T read(int id, Class<T> entityClass)
    {
        T obj = null;

        String json = "";

        String qualifiedTableName = genQualifiedTableNameFrom(entityClass);

        String sqlReadDataset = "SELECT Json FROM " + qualifiedTableName + " WHERE ID=?";

        try(PreparedStatement readStmt = mySQLAdapter.getConnection().prepareStatement(sqlReadDataset))
        {
            readStmt.setInt(1, id);

            try(ResultSet resultSet = readStmt.executeQuery())
            {
                if(resultSet.next())
                {
                    json = resultSet.getString("Json");
                }
            }
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }

        try
        {
            obj = gson.fromJson(json, entityClass);
        }
        catch(JsonSyntaxException e)
        {
            e.printStackTrace();
        }

        // Null if:
        // 1. no dataset was found with that given ID or
        // 2. there are more datasets with the same ID which is nearly impossible (assuming table schema correct)
        return obj;
    }

    @Override
    public boolean delete(int id, Class<T> entityClass)
    {
        String qualifiedTableName = genQualifiedTableNameFrom(entityClass);
        String sqlDelete = "DELETE FROM " + qualifiedTableName + " WHERE ID=?";
        boolean success = false;

        try(PreparedStatement deleteStmt = mySQLAdapter.getConnection().prepareStatement(sqlDelete))
        {
            deleteStmt.setInt(1, id);

            int rowsAffected = deleteStmt.executeUpdate();

            if(rowsAffected > 1)
            {
                mySQLAdapter.getConnection().rollback();
            }
            else
            {
                mySQLAdapter.getConnection().commit();

                success = rowsAffected == 1;
            }
        }
        catch(SQLException exc)
        {
            exc.printStackTrace();
        }

        return success;
    }

    @Override
    public int[] listNumericJsonDatasets(Class<T> entityClass) throws IllegalArgumentException
    {
        String qualifiedTableName = genQualifiedTableNameFrom(entityClass);
        String sqlReadAll = "SELECT ID FROM " + qualifiedTableName;

        ArrayList<Integer> IDs = new ArrayList<>();

        try(Statement readStmt = mySQLAdapter.getConnection().createStatement())
        {
            try(ResultSet rs = readStmt.executeQuery(sqlReadAll))
            {
                while(rs.next())
                {
                    IDs.add(rs.getInt("ID"));
                }
            }
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }

        return IDs.stream().mapToInt(value -> value).toArray();
    }

    @Override
    public int getNewestNumberFromJsons(Class<T> entityClass)
    {
        int[] numericJsonDatasets = listNumericJsonDatasets(entityClass);

        if(numericJsonDatasets.length == 0)
        {
            return -1; // No JSON datasets do exist yet for any domain object.
        }

        int highestIdFound = Integer.MIN_VALUE;

        for(int dataset : numericJsonDatasets)
        {
            if(dataset > highestIdFound)
            {
                highestIdFound = dataset;
            }
        }

        return highestIdFound;
    }
}