package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import java.sql.SQLException;

public interface IDatabaseAdapter
{
    /**
     * Creates a database connection.
     * Usually,
     * only one call to this function is necessary for a keep-alive connection to your DB.
     * You can anyway call this function more than one time for a completely new connection (e.g. with different credentials).
     * Uses the charset "utf8mb4" for the connection.
     *
     * The PDO object is used for preparing statements,
     * executing queries,
     * and fetching results.
     * Saves the PDO object in a private variable for re-use internally.
     *
     * Can throw an PDOException.
     *
     * @param host Your database host, e.g. "database-0123456789.webspace-host.com"
     * @param databaseName Your database name, e.g. "xampp" (usually is if just installed)
     * @param username Your username, e.g. "root" or "postgres"
     * @param password Your password, e.g. "myPassword123" or "myPassword"
     * @throws SQLException If the database connection fails (e.g., wrong credentials set).
     */
    public void connect(String host, String databaseName, String username, String password);

    /**
     * Executes a given SQL statement to read from the database (e.g., SELECT).
     * Parameters (if any) are bound to the statement in the order of the question marks (?).
     * Safe against SQL injection since the statement is prepared before execution.
     * PDO automatically detects PHP variable types; you usually do not need to worry about them.
     *
     * Example:
     *
     * $sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";
     *
     * $rows = $this->fetch($sql, [$username, $password]);
     *
     * Notes:
     * - Each row is returned as an **associative array** indexed by column name (PDO::FETCH_ASSOC).
     * - If multiple columns have the same name, only one value per column name is returned.
     *
     * Can throw an PDOException.
     *
     * @param sql    SQL statement, optionally containing ? placeholders for parameters.
     * @param params Optional array of parameters to replace the ? placeholders.
     * @return array         Resulting rows as an array of associative arrays (each row / associative array accessible numerically from 0 to ...).
     * @throws SQLException  If connection fails or SQL execution fails.
     */
    public Object[] fetch(String sql, Object[] params);

    /**
     * Executes a given SQL statement to create, update or delete something in the database (e.g., INSERT).
     * Parameters (if any) are bound to the statement in the order of the question marks (?).
     * Safe against SQL injection since the statement is prepared before execution.
     * PDO automatically detects PHP variable types; you usually do not need to worry about them.
     *
     * Example:
     *
     * $sql = "INSERT INTO User(Username, Password) VALUES(?, ?)";
     *
     * $rows = $this -> execute($sql, [$username, $password]);
     *
     * Can throw an PDOException.
     *
     * @param sql SQL statement, optionally containing ? placeholders for parameters.
     * @param params Array of parameters to replace the ? placeholders.
     * @return int The amount of affected rows, e.g. created, updated or deleted.
     * @throws SQLException  If connection fails or SQL execution fails.
     */
    public int execute(String sql, Object[] params);

    /**
     * Tells you the technical character limit of an SQL column.
     * Meant to work with data type VARCHAR.
     *
     * @param tableName SQL table name.
     * @param columnName SQL column name.
     * @return int Character limit of the column in that given table. -1 if something went wrong while querying or processing it.
     * @throws SQLException  If connection fails or SQL execution fails.
     */
    public int readCharacterLimit(String tableName, String columnName);

    /**
     * Tells you all column names of a given table.
     * If the first column is named "id" (case-insensitively seen),
     * then it will not be included.
     *
     * @param tableName SQL table name.
     * @return String[] All column names as an array.
     * @throws SQLException If SQL execution fails for some reason.
     */
    public String[] readColumnNames(String tableName);

    /**
     * Tells you the datatype of a column.
     *
     * @param tableName Database table name
     * @param columnName Column name
     * @return string The datatype of that column. Or empty string if it could not be determined.
     */
    public String readDatatype(String tableName, String columnName);

    /**
     * @param tableName
     * @param columnName
     * @return boolean
     */
    public boolean isString(String tableName, String columnName);
}
