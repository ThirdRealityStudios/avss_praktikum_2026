package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

public interface IJsonSQLService<T>
{
    /**
     * Saves the given object serialized as a JSON string to the database.
     * Automatically detects the type of your object.
     *
     * @param obj The object to save.
     * @return The ID of the created object dataset after success. On failure, returns -1 (could not create dataset - for whatever reason).
     */
    public int save(String table, Object obj);

    /**
     * Reads a desired object from your database as a object with the given type.
     *
     * @param table The name of the table in that database
     * @param id The ID to load the desired object.
     * @param className Type of the object to read it properly.
     * @return The object from that given ID. Otherwise, null (e.g. if dataset not found or technical issues).
     */
    public T read(String table, int id, Class<T> className);

    /**
     * Deletes the given object.
     * Although you could delete any file with this method
     * (which it was not intended for).
     *
     * @param id The ID of the dataset object.
     * @return On success of deletion, true is returned. Otherwise, false.
     */
    public boolean delete(String table, int id);

    /**
     * Returns all IDs of datasets from that given table.
     * If there are no datasets,
     * then an empty ID array is returned.
     *
     * @param table Table to read all IDs from.
     * @return All IDs of datasets from that given table.
     * @throws IllegalArgumentException E.g. the given table is invalid.
     */
    public int[] listNumericJsonDatasets(String table) throws IllegalArgumentException;
}
