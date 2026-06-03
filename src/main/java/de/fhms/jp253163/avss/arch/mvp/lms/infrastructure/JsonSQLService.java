package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.ArrayUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.function.ToIntFunction;

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

        mySQLAdapter = new MySQLAdapter("0.0.0.0:33060", "lms");

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

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + tableName + "( ID INT(8) AUTO_INCREMENT PRIMARY KEY UNIQUE , Json TEXT NOT NULL );";

        Statement stmt = mySQLAdapter.getConnection().createStatement();
        if(stmt != null)
        {
            stmt.executeUpdate(sqlCreateTable);
            stmt.getConnection().commit();
            stmt.close();
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

        String qualifiedTableName = mySQLAdapter.getDatabaseName() + "." + genTableNameFrom(obj.getClass());

        String json = gson.toJson(obj);

        // Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
        //
        // Btw: you cannot use question mark placeholder in sql statement for a table name!
        // Only where normal values would be expected.

        String sqlInsertJson = "INSERT IGNORE INTO " + qualifiedTableName + "(ID, Json) VALUES(?, ?)";

        // UPDATE CompanyModel SET $name=? WHERE CompanyModel.ID=?
        String sqlUpdateJson = "UPDATE " + qualifiedTableName + " SET Json=? WHERE ID = ?";

        try(PreparedStatement stmtInsert = mySQLAdapter.getConnection().prepareStatement(sqlInsertJson))
        {
            stmtInsert.setInt(1, id);
            stmtInsert.setString(2, json);

            int insertRowsAffected = stmtInsert.executeUpdate();

            // See if there is yet a dataset (so no rows affected by insertion):
            if(insertRowsAffected == 0)
            {
                // Need to make an update instead:
                try(PreparedStatement stmtUpdate = mySQLAdapter.getConnection().prepareStatement(sqlUpdateJson))
                {
                    stmtUpdate.setString(1, json);
                    stmtUpdate.setInt(2, id);

                    int updateRowsAffected = stmtUpdate.executeUpdate();

                    if(updateRowsAffected == 1)
                    {
                        mySQLAdapter.getConnection().commit();

                        stmtInsert.close();
                        stmtUpdate.close();

                        return true;
                    }
                    else
                    {
                        mySQLAdapter.getConnection().rollback();
                    }
                }
                catch (Exception exc)
                {
                    mySQLAdapter.getConnection().rollback();
                }
                finally
                {
                    stmtInsert.getConnection().close();
                }

                return false;
            }

            stmtInsert.getConnection().commit();
            stmtInsert.close();

            return true;
        }
        catch (Exception exc)
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

        String tableName = genTableNameFrom(entityClass);
        String qualifiedTableName = mySQLAdapter.getDatabaseName() + "." + tableName;

        String sqlReadDataset = "SELECT Json FROM " + qualifiedTableName + " WHERE ID=?";

        try(PreparedStatement readStmt = mySQLAdapter.getConnection().prepareStatement(sqlReadDataset))
        {
            readStmt.setInt(1, id);

            ResultSet resultSet = readStmt.executeQuery();

            if(resultSet.next())
            {
                json = resultSet.getString("Json");
            }

            mySQLAdapter.getConnection().commit();
            resultSet.close();
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
        String qualifiedTableName = mySQLAdapter.getDatabaseName() + "." + genTableNameFrom(entityClass);
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

            deleteStmt.close();
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
        String qualifiedTableName = genTableNameFrom(entityClass);
        String sqlReadAll = "SELECT ID FROM " + qualifiedTableName;

        ArrayList<Integer> IDs = new ArrayList<>();

        try(Statement readStmt = mySQLAdapter.getConnection().createStatement())
        {
            ResultSet rs = readStmt.executeQuery(sqlReadAll);

            while(rs.next())
            {
                IDs.add(rs.getInt("ID"));
            }

            mySQLAdapter.getConnection().commit();
            readStmt.close();
        }
        catch (SQLException exc)
        {
            exc.printStackTrace();
        }

        return IDs.stream().mapToInt(value -> value).toArray();
    }
}
