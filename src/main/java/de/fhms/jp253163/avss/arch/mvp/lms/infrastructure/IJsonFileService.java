package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import java.io.File;
import java.io.IOException;

public interface IJsonFileService<T>
{
    /**
     * Saves the given object to the given filepath as a JSON file.
     * Automatically detects the type of your object.
     *
     * @param jsonFilepath
     * @param obj
     * @return
     */
    public boolean save(String jsonFilepath, Object obj);

    /**
     * Reads a given JSON file and returns it as an object.
     *
     * @param jsonFilepath The filepath to the JSON file.
     * @param className Type of the object to create it.
     * @return The object from that given filepath. Otherwise, null (e.g. if file not found or technical issues).
     */
    public T read(String jsonFilepath, Class<T> className);

    /**
     * Deletes the given (JSON) file.
     * Although you could delete any file with this method
     * (which it was not intended for).
     *
     * @param jsonFilepath
     * @return
     */
    public boolean delete(String jsonFilepath);

    /**
     * Returns all JSON files from that given directory.
     * If there are no JSON files,
     * then an empty File array is returned.
     *
     * @param directory
     * @return
     * @throws IOException E.g. the given directory is invalid.
     */
    public File[] listNumericJsonFiles(File directory) throws IOException;

    /**
     * Returns only the number contained in the filename of that given JSON file.
     * If the given file does not represent a numeric filename or is invalid (e.g. null),
     * then null will be returned.
     *
     * @param numericallyNamedJson
     * @return Number contained in the JSON filename. Otherwise, null (file is not ending on .json, is not numerically named etc.)
     */
    public String getNumberFromJsonName(File numericallyNamedJson);

    /**
     * Returns the highest ID found for a Borrower object saved as a JSON file.
     *
     * @param numericJsonsDirectory The directory of all the JSON files being numerically named (e.g. "1.json", "12.json").
     * @return Highest Borrower ID known to the system (judging by the JSON file name). If there is no Borrower object saved yet, then -1 is returned (you can freely choose ID). If there was a technical error, -2 is returned.
     * @throws IOException
     */
    public int getNewestNumberFromJsons(File numericJsonsDirectory) throws IOException;
}
