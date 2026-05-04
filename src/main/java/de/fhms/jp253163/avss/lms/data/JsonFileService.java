package de.fhms.jp253163.avss.lms.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFileService<T> implements IJsonFileService<T>
{
    private GsonBuilder gsonBuilder;
    private Gson gson;
    private FileWriter writer;
    private FileReader reader;

    public JsonFileService(GsonBuilder gsonBuilder) throws IllegalArgumentException
    {
        if(gsonBuilder == null)
            throw new IllegalArgumentException("argument must not be null!");

        gsonBuilder.setPrettyPrinting();
        this.gsonBuilder = gsonBuilder;

        this.gson = gsonBuilder.create();
    }

    @Override
    public boolean save(String jsonFilepath, Object obj)
    {
        String json = null;

        json = gson.toJson(obj);

        try
        {
            writer.write(json);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    public T read(String jsonFilepath, Class<T> className)
    {
        T obj = null;
        String json = null;

        try
        {
            json = reader.readAllAsString();
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return null;
        }

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
    public boolean delete(String jsonFilepath)
    {
        Path path = Path.of(jsonFilepath);
        boolean deletetionSuccess = false;

        try
        {
            deletetionSuccess = Files.deleteIfExists(path);
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return false;
        }

        return deletetionSuccess;
    }
}
