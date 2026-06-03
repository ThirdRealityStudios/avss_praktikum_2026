package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
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

        mySQLAdapter = new MySQLAdapter("0.0.0.0:33060", "lms");

        if(!mySQLAdapter.connect("root", "password"))
            throw new SQLException("Connection failed!");
    }

    private String genTableNameFrom(Object obj)
    {
        Objects.requireNonNull(obj);

        String classCanonicalName = obj.getClass().getCanonicalName();

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

        String tableName = genTableNameFrom(obj);

        Objects.requireNonNull(tableName);

        if(tableName.isEmpty())
            throw new NullPointerException("Table name cannot be empty (internal error)");

        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + tableName + "( ID INT(8) AUTO_INCREMENT PRIMARY KEY UNIQUE, Hash , Json TEXT NOT NULL );";

        Statement stmt = null;
        stmt = mySQLAdapter.getConnection().createStatement();
        if(stmt != null)
        {
            stmt.executeUpdate(sqlCreateTable);
            stmt.close();
        }
    }

    private String hash(String str) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] digest = md.digest(str.getBytes());

        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    @Override
    public int save(int id, Object obj)
    {
        Objects.requireNonNull(obj);

        try
        {
            ensureTableFor(obj);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();

            return -1;
        }

        String qualifiedTableName = mySQLAdapter.getDatabaseName() + "." + genTableNameFrom(obj);

        String json = gson.toJson(obj);

        // Tutorial: https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
        //
        // Btw: you cannot use question mark placeholder in sql statement for a table name!
        // Only where normal values would be expected.

        String sqlInsertJson = "INSERT IGNORE INTO " + qualifiedTableName + "(Code) VALUES(?, ?)";

        // UPDATE CompanyModel SET $name=? WHERE CompanyModel.ID=?
        String sqlUpdateJson = "UPDATE " + qualifiedTableName + " SET Code=?";

        try(PreparedStatement stmt = mySQLAdapter.getConnection().prepareStatement(sqlInsertJson))
        {
            stmt.setString(1, json);

            int rowsAffected = stmt.executeUpdate();

            // See if there is yet a dataset (so no rows affected by insertion):
            if(rowsAffected == 0)
            {
                // Need to make an update instead:
                stmt = mySQLAdapter.getConnection().prepareStatement();
                // TODO Implement
            }
        }
        catch (Exception exc)
        {
            return -1; // error, could not save.
        }

        return -1;
    }

    @Override
    public T read(int id, Class<T> className)
    {
        T obj = null;

        String json = "";

        try
        {
            obj = gson.fromJson(json, className);
        }
        catch(JsonSyntaxException e)
        {
            e.printStackTrace();

            return null;
        }

        return obj;
    }

    @Override
    public boolean delete(int id)
    {
        return false;
    }

    @Override
    public int[] listNumericJsonDatasets(Class<T> entityClass) throws IllegalArgumentException
    {
        return new int[0];
    }
}
