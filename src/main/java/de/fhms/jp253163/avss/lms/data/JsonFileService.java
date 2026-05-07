package de.fhms.jp253163.avss.lms.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class JsonFileService<T> implements IJsonFileService<T>
{
    private GsonBuilder gsonBuilder;
    private Gson gson;
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
        FileWriter writer = null;
        File file = null;

        json = gson.toJson(obj);

        try
        {
            file = new File(jsonFilepath);
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();

            return false;
        }

        try
        {
            writer = new FileWriter(file);

            writer.write(json);

            writer.flush();

            writer.close();
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
        FileReader reader = null;
        File file = null;
        T obj = null;
        String json = null;

        try
        {
            file = new File(jsonFilepath);
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();

            return null;
        }

        try
        {
            reader = new FileReader(file);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();

            return null;
        }

        try
        {
            json = reader.readAllAsString();

            reader.close();
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

    /**
     * @param file File to test for a valid JSON extension
     * @return true if the given file has a JSON file extension. Otherwise, false.
     */
    private boolean hasExtensionJSON(File file)
    {
        String extension = StringUtils.substringAfterLast(file.getName(), ".");

        return extension.equalsIgnoreCase("json");
    }

    @Override
    public File[] listNumericJsonFiles(File directory) throws IOException
    {
        IOException e = new IOException("cannot work in that file system environment. Are all files valid? Do they exist?");

        if(directory == null || !directory.isDirectory() || !directory.exists())
            throw e;

        File[] directoryFiles = directory.listFiles();
        String iteratedFilenameNaked = null;

        ArrayList<File> jsonFilesFound = new ArrayList<>();

        if(directoryFiles == null)
            throw e;

        for(File file : directoryFiles)
        {
            iteratedFilenameNaked = getNumberFromJsonName(file);

            if(iteratedFilenameNaked == null)
            {
                continue; // skip (is directory, is no JSON file or is not numerically named)
            }

            jsonFilesFound.add(file);
        }

        return jsonFilesFound.toArray(new File[0]);
    }

    @Override
    public String getNumberFromJsonName(File numericallyNamedJson)
    {
        String filenameNaked = null;

        filenameNaked = StringUtils.substringBeforeLast(numericallyNamedJson.getName(), ".");

        if(numericallyNamedJson.isDirectory() || !hasExtensionJSON(numericallyNamedJson) || !StringUtils.isNumeric(filenameNaked))
        {
            System.out.println(filenameNaked + " is NO valid JSON!!!");

            return null; // not an expected file ending on .json
        }

        System.out.println(filenameNaked + " is VALID NUMERIC JSON!!!");

        return filenameNaked;
    }

    @Override
    public int getNewestNumberFromJsons(File numericJsonsDirectory) throws IOException
    {
        File[] numericJsonFiles = listNumericJsonFiles(numericJsonsDirectory);
        String filenameNumber = null;

        if(numericJsonFiles.length == 0)
        {
            return -1; // No JSON files do exist yet for any IBorrower object.
        }

        int highestIdFound = Integer.MIN_VALUE;
        Integer newlyFoundId = Integer.MIN_VALUE;

        for(File file : numericJsonFiles)
        {
            filenameNumber = getNumberFromJsonName(file);

            if(filenameNumber == null)
            {
                throw new IOException("expected numerically named JSON file but got invalid instead");
            }

            // Remember the JSON filename.
            newlyFoundId = Integer.getInteger(filenameNumber);

            if(newlyFoundId == null)
                return -2;

            if(newlyFoundId > highestIdFound)
            {
                highestIdFound = newlyFoundId;
            }
        }

        return highestIdFound;
    }
}
