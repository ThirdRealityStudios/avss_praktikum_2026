package de.fhms.jp253163.avss.arch.mvp.lms.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLAdapter
{
    private String connectionString;
    private Connection con;

    /**
     * @param host E.g. "0.0.0.0:33060"
     * @param databaseName E.g. "lms" for loan management system
     */
    public MySQLAdapter(String host, String databaseName)
    {
        // $this -> pdo = new PDO("mysql:host={$host};dbname={$databaseName};charset=utf8mb4", $username, $password, [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION, PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC]);
        connectionString = "jdbc:mysql://" + host + "/" + databaseName;

        System.out.println("MySQLAdapter: connection string is \"" + connectionString + "\"");
    }

    /**
     * @param user
     * @param password
     * @return True if connected successfully. Otherwise, false.
     */
    public boolean connect(String user, String password)
    {
        try
        {
            con = DriverManager.getConnection(connectionString, user, password);

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
}
