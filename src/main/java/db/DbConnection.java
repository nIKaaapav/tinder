package db;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    @SneakyThrows
   public static Connection create(String jdbc_url) throws SQLException {
       return DriverManager.getConnection(jdbc_url);
   }
}
