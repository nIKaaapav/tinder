package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
    public static final String URL = "jdbc:postgresql://ec2-54-160-96-70.compute-1.amazonaws.com:5432/d3f5i1si31cu2r?password=08e82ee554cc6d7797e7ddbc5967426b462960fe4e28fccfaeb7f294e1d1df35&sslmode=require&user=bzqpbltonvtiqf";
    public static final String USER = "bzqpbltonvtiqf";
    public static final String PASS = "08e82ee554cc6d7797e7ddbc5967426b462960fe4e28fccfaeb7f294e1d1df35";

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection()
    {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

}
