package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class MySQLAdapter
{
    private String connectionString;
    private String host, databaseName;
    private Connection con;

    /**
     * @param host E.g. "0.0.0.0:33060"
     * @param databaseName E.g. "lms" for loan management system
     */
    public MySQLAdapter(String host, String databaseName)
    {
        Objects.requireNonNull(host);
        Objects.requireNonNull(databaseName);

        this.databaseName = databaseName;
        this.host = host;

        // $this -> pdo = new PDO("mysql:host={$host};dbname={$databaseName};charset=utf8mb4", $username, $password, [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION, PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC]);
        connectionString = "jdbc:mysql://" + host + "/" + databaseName;

        System.out.println("MySQLAdapter: connection string is \"" + connectionString + "\"");
    }

    /**
     * Connects to the DB,
     * using autoCommit = false.
     * So you can work as normal,
     * change values etc.
     * But you can use e.g. con.rollback() in order to revert corrupt changes before writing them in real to the DB.
     * If CRUD operations are ok,
     * then simply use con.commit() in the end.
     * Note: you can do multiple CRUD operations one by another.
     *       You can also see the theoretical changes in between the operations
     *       (just like you would execute the CRUD operations directly in realtime).
     *       But in difference to without autoCommit = true,
     *       you can still revert changes especially.
     *       In the end just use con.commit() to write all changes in reality to the DB.
     *
     * @param user
     * @param password
     * @return True if connected successfully. Otherwise, false.
     */
    public boolean connect(String user, String password) throws SQLException
    {
        try
        {
            con = DriverManager.getConnection(connectionString, user, password);
            con.setAutoCommit(false);

            return true;
        }
        catch(Exception exc)
        {
            return false;
        }
    }

    public Connection getConnection()
    {
        return con;
    }

    public String getHost()
    {
        return host;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }
}
