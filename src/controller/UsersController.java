package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DataSource;
import model.ManagerModel;
import model.User;
import view.View;

public class UsersController implements Controller<User> {
    private final ManagerModel model;
    private final View view;

    public UsersController(ManagerModel model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            throw new RuntimeException("Failed to create user", e);
        }
    }

    @Override
    public User read(int id) {
        String sql = "SELECT id, username, password FROM User WHERE id = ?";
        User user = null;
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error reading user: " + e.getMessage());
            throw new RuntimeException("Failed to read user", e);
        }
        
        return user;
    }

    /**
     * Updates an existing user's information in the database.
     * Students: Use an UPDATE SQL statement to modify the user's data based on
     * their ID.
     * 
     * @param user The User object containing updated information.
     */
    @Override
    public void update(User user) {
        String sql = "UPDATE User SET username = ?, password = ? WHERE id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            throw new RuntimeException("Failed to update user", e);
        }
    }

    /**
     * Deletes a user from the database by their ID.
     * Students: Use a DELETE SQL statement with a WHERE clause on the user's ID.
     * 
     * @param id The unique identifier of the user to be deleted.
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        
        try (Connection conn = DataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    /**
     * Retrieves all users from the database.
     * Students: Use a SELECT * FROM users query, iterate through the ResultSet,
     * and create a list of User objects to return.
     * 
     * @return A list of all User objects in the database.
     */
    public List<User> getAllUsers() {
        String sql = "SELECT id, username, password FROM User";
        List<User> users = new ArrayList<>();
        
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
            System.err.println("Error retrieving users: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve users", e);
        }
        
        return users;
    }
}