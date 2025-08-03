package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManagerModel implements Model {
    private final Connection connection;

    public TaskManagerModel() {
        try {
            this.connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public int getId() {
        return 0; // Not used for the model itself
    }

    // Task-related methods
    public void addTask(String title, String description) {
        String sql = "INSERT INTO Task (title, description, user_id, completed) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, 1); // Default user ID
            pstmt.setInt(4, 0); // 0 for not completed
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
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

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, 1); // Default user ID
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    null, // createdAt
                    null, // updatedAt
                    null, // dueAt
                    rs.getInt("completed") == 1 ? "done" : "pending",
                    rs.getInt("user_id")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks from database", e);
        }
        
        return tasks;
    }

    public Task getTaskById(int id) {
        String sql = "SELECT * FROM Task WHERE id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, 1); // Default user ID
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Task(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    null, // createdAt
                    null, // updatedAt
                    null, // dueAt
                    rs.getInt("completed") == 1 ? "done" : "pending",
                    rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving task from database", e);
        }
        
        return null;
    }

    public boolean updateTask(int id, String title, String description) {
        String sql = "UPDATE Task SET title = ?, description = ? WHERE id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setInt(3, id);
            pstmt.setInt(4, 1); // Default user ID
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task in database", e);
        }
    }

    public boolean deleteTask(int id) {
        String sql = "DELETE FROM Task WHERE id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, 1); // Default user ID
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting task from database", e);
        }
    }
    
    public boolean updateTaskStatus(int id, String status) {
        String sql = "UPDATE Task SET completed = ? WHERE id = ? AND user_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, "done".equals(status) ? 1 : 0);
            pstmt.setInt(2, id);
            pstmt.setInt(3, 1); // Default user ID
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task status in database", e);
        }
    }

    // User-related methods
    public void addUser(String username, String password) {
        String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving user from database", e);
        }
        
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        
        try (Statement stmt = connection.createStatement();
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
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user from database", e);
        }
    }

    @Override
    public boolean save() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
