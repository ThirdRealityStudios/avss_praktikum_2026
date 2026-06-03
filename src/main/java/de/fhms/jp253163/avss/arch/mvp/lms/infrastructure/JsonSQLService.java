package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.sql.*;

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

    @Override
    public int save(String table, Object obj)
    {
        String json = null;

        json = gson.toJson(obj);

        mySQLAdapter.getConnection().

        return -1;
    }

    @Override
    public T read(String table, int id, Class<T> className)
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
    public boolean delete(String table, int id)
    {
        return false;
    }

    @Override
    public int[] listNumericJsonDatasets(String table) throws IllegalArgumentException
    {
        return new int[0];
    }
}
