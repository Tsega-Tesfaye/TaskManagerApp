package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerModel implements Model {
    public ManagerModel() {
    }

    public int getId() {
        return 0; // Not used for the model itself
    }

    // Authentication related method
    public User authenticateUser(String username, String password) {
            String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
            
            try (Connection conn = DataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                
                try (ResultSet rs = pstmt.executeQuery()) { 
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error authenticating user: " + e.getMessage());
            }
            return null; // Return null if authentication fails
        }


    
    
    // Task-related methods
    public void addTask(String title, String description, int userId) {
        String sql = "INSERT INTO Task (title, description, user_id, completed) VALUES (?, ?, ?, ?)";

        /*
        INSERT INTO Task (title, description, user_id, completed) VALUES ("task", "desc", 1, 0)
        */
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, userId); // Use the provided user ID
            pstmt.setInt(4, 0); // 0 for not completed
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) { // { 1, 2 }
                if (generatedKeys.next()) {
                    generatedKeys.getInt(1); // Get the generated ID
                } else {
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding task to database", e);
        }
    }

    public List<Task> getAllTasks(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE user_id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId); // Use the provided user ID
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setStatus(rs.getInt("completed") == 1 ? "done" : "pending");
                task.setUserId(rs.getInt("user_id"));

                tasks.add(task);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving tasks: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve tasks", e);
        }
        
        return tasks;
    }

    public Task getTaskById(int taskId, int userId) {
        String sql = "SELECT * FROM Task WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.setInt(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setStatus(rs.getInt("completed") == 1 ? "done" : "pending");
                    task.setUserId(rs.getInt("user_id"));
                    return task;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving task: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve task", e);
        }
        
        return null;
    }

    public boolean updateTask(int id, String title, String description, int userId) {
        String sql = "UPDATE Task SET title = ?, description = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, id);
            pstmt.setInt(4, userId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating task: " + e.getMessage());
            throw new RuntimeException("Failed to update task", e);
        }
    }

    public boolean deleteTask(int id, int userId) {
        String sql = "DELETE FROM Task WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting task: " + e.getMessage());
            throw new RuntimeException("Failed to delete task", e);
        }
    }
    
    public boolean updateTaskStatus(int id, String status, int userId) {
        String sql = "UPDATE Task SET completed = ? WHERE id = ? AND user_id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, "done".equals(status) ? 1 : 0);
            pstmt.setInt(2, id);
            pstmt.setInt(3, userId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating task status: " + e.getMessage());
            throw new RuntimeException("Failed to update task status", e);
        }
    }


    // User-related methods
    public void addUser(String username, String password) {
        String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedKeys.getInt(1); // Get the generated ID
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding user to database", e);
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user from database", e);
        }
        
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        
        try (Connection conn = DataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving users from database", e);
        }
        
        return users;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE User SET username = ?, password = ? WHERE id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating user in database", e);
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user from database", e);
        }
    }
}
