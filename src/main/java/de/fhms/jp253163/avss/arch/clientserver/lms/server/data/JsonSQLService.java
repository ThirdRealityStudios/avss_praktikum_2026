package de.fhms.jp253163.avss.arch.clientserver.lms.server.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

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

    /**
     * @param entityClass
     * @return The SHA-256 hash as a hexadecimal string. Or null if creation went wrong.
     * @param <E>
     * @throws NoSuchAlgorithmException If SHA-256 could not be found.
     */
    private <E> String hash(Class<E> entityClass) throws NoSuchAlgorithmException
    {
        byte[] nameInBytes = entityClass.getName().getBytes(StandardCharsets.UTF_8);
        byte[] hashCode = MessageDigest.getInstance("SHA-256").digest(nameInBytes);

        String hashHex = "";

        for(byte hashByte : hashCode)
        {
            hashHex += String.format("%02x", hashByte);
        }

        return hashHex.isEmpty() ? null : hashHex;
    }

    private <E> String genTableNameFrom(Class<E> entityClass) throws NoSuchAlgorithmException
    {
        Objects.requireNonNull(entityClass);

        return hash(entityClass);
    }

    /**
     * Additionally to genTableNameFrom(..),
     * this also adds the database name before the "canonical" table name.
     * This helps your DB runtime environment to know which database is selected for executions.
     *
     * @param entityClass
     * @return database name + . + "canonical" table name
     * @param <E>
     */
    private <E> String genQualifiedTableNameFrom(Class<E> entityClass) throws NoSuchAlgorithmException
    {
        return mySQLAdapter.getDatabaseName() + "." + genTableNameFrom(entityClass);
    }

    /**
     * Should be called before any CRUD action in this class
     * which requires corresponding access.
     *
     * @param entityClass
     * @throws NullPointerException
     * @throws SQLException
     */
    private <E> void ensureTableFor(Class<E> entityClass) throws NullPointerException, SQLException, NoSuchAlgorithmException
    {
        Objects.requireNonNull(entityClass);

        String qualifiedTableName = genQualifiedTableNameFrom(entityClass);

        Objects.requireNonNull(qualifiedTableName);

        if(qualifiedTableName.isEmpty())
            throw new NullPointerException("Table name cannot be empty (internal error)");

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + qualifiedTableName + "( ID INT(8) PRIMARY KEY UNIQUE , Json TEXT NOT NULL );";

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
            ensureTableFor(obj.getClass());
        }
        catch (Exception exc)
        {
            exc.printStackTrace();

            return false;
        }

        String qualifiedTableName = null;

        try
        {
            qualifiedTableName = genQualifiedTableNameFrom(obj.getClass());
        }
        catch (NoSuchAlgorithmException exc)
        {
            exc.printStackTrace();

            return false;
        }

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

            stmtInsert.executeUpdate();

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

        String qualifiedTableName = null;

        try {
            qualifiedTableName = genQualifiedTableNameFrom(entityClass);
        }
        catch (NoSuchAlgorithmException exc)
        {
            exc.printStackTrace();

            return null;
        }

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
        String qualifiedTableName = null;
        boolean success = false;

        try
        {
            qualifiedTableName = genQualifiedTableNameFrom(entityClass);
        }
        catch (NoSuchAlgorithmException exc)
        {
            exc.printStackTrace();

            return false;
        }

        String sqlDelete = "DELETE FROM " + qualifiedTableName + " WHERE ID=?";

        try(PreparedStatement deleteStmt = mySQLAdapter.getConnection().prepareStatement(sqlDelete))
        {
            deleteStmt.setInt(1, id);

            int rowsAffected = deleteStmt.executeUpdate();

            if(rowsAffected == 0)
            {
                mySQLAdapter.getConnection().rollback();

                success = false;
            }
            else
            {
                mySQLAdapter.getConnection().commit();

                success = true;
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
        String qualifiedTableName = null;

        try
        {
            ensureTableFor(entityClass);

            qualifiedTableName = genQualifiedTableNameFrom(entityClass);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();

            return new int[0];
        }

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