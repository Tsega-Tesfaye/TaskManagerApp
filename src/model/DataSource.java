package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    
    private static Connection connection = null;


    private static final String URL = "jdbc:sqlite:taskmanager.db";

    private DataSource() {
        
    }

    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC Driver not found. Make sure it is in your classpath.", e);
            }
            
            connection = DriverManager.getConnection(URL);
        }
        return connection;
    }


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
            
            stmt.execute(userTable);
            
            stmt.execute(taskTable);
        }
    }
}
