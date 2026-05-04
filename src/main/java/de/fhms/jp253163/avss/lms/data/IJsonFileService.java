package de.fhms.jp253163.avss.lms.data;

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
}
