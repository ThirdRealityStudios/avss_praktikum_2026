package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import java.io.File;
import java.io.IOException;

public interface IJsonSQLService<T>
{
    /**
     * Saves the given object serialized as a JSON string to the database.
     * The target table is determined by the canonical name of the given object and existence is ensured before saving.
     * Automatically detects the type of your object.
     * An ID is required to save (ignoring creation or update here).
     * Ideally, you would use the same ID as for your domain objects (if matters).
     *
     * @param id The ID to use for saving the object (about the equivalent to the filepath in IJsonFileService).
     * @param obj The object to save.
     * @return If object could be saved, then true is returned. On failure, returns false (could not create dataset - for whatever reason).
     */
    public boolean save(int id, Object obj);

    /**
     * Reads a desired object from your database as a object with the given type.
     * The source table is determined by the canonical name of the given object and existence is ensured before reading.
     *
     * @param id The ID to load the desired object.
     * @param className Type of the object to read it properly.
     * @return The object from that given ID. Otherwise, null (e.g. if dataset not found or technical issues).
     */
    public T read(int id, Class<T> className);

    /**
     * Deletes the given object.
     * The target table is determined by the canonical name of the given object and existence is ensured before deletion.
     *
     * @param id The ID of the dataset object.
     * @param className Type of the object to find and delete it properly.
     * @return On success of deletion, true is returned. Otherwise, false.
     */
    public boolean delete(int id, Class<T> className);

    /**
     * Returns all IDs of datasets from the determined table.
     * The source table is determined by the canonical name of the given object and existence is ensured before reading.
     * If there are no datasets,
     * then an empty ID array is returned.
     *
     * @return All IDs of datasets for the given object type.
     */
    public int[] listNumericJsonDatasets(Class<T> entityClass);

    /**
     * Returns the highest ID found for a domain object saved as a JSON file.
     *
     * @param entityClass Determines the table where to look at.
     * @return Highest Borrower ID known to the system (judging by the dataset IDs simply). If there is no domain object saved yet, then -1 is returned (you can freely choose ID).
     * @throws IOException
     */
    int getNewestNumberFromJsons(Class<T> entityClass);
}
