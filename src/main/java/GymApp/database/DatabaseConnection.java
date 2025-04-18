package GymApp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections to the PostgreSQL gym system.
 * <p>
 * Provides a static method to establish a connection using JDBC.
 * </p>
 */

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/s3javafinal";
    private static final String USER = "postgres";
    private static final String PASSWORD = "KeyinSD12";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            System.out.println("Connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
