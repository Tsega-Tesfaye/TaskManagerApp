package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a singleton SQLite database connection.
 */
public class DataSource {
    // Holds the singleton connection instance
    private static Connection connection = null;

    // Path to the SQLite database file. Students: Change the path if your database
    // file is elsewhere.
    private static final String URL = "jdbc:sqlite:taskmanager.db";

    private DataSource() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns a singleton Connection instance to the SQLite database.
     * Students: This method ensures only one connection is used throughout the app.
     * If the connection is closed or null, it creates a new one.
     *
     * @return Connection object to the SQLite database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC Driver not found. Make sure it is in your classpath.", e);
            }
            // Create a connection to the SQLite database
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }

    /**
     * Initializes the database by creating the User and Task tables if they do not exist.
     * Students: Call this method at the start of your application to ensure the tables are present.
     * You can modify the schema as needed for your project requirements.
     */
    public static void initialize() throws SQLException {
        String userTable = "CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL" +
                ");";

        String taskTable = "CREATE TABLE IF NOT EXISTS Task (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT," +
                "user_id INTEGER NOT NULL," +
                "completed INTEGER DEFAULT 0," +
                "FOREIGN KEY(user_id) REFERENCES User(id)" +
                ");";

        try (Connection conn = getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            // Create User table
            stmt.execute(userTable);
            // Create Task table
            stmt.execute(taskTable);
        }
    }
}
