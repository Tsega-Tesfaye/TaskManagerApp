package controller;

import model.Task;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.DataSource;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TasksController implements Controller<Task> {

    @Override
    public void create(Task task) {
        String sql = "INSERT INTO Task (title, description, createdAt, updatedAt, dueAt, status, userId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getCreatedAt().toString());
            stmt.setString(4, task.getUpdatedAt().toString());
            stmt.setString(5, task.getDueAt().toString());
            stmt.setString(6, task.getStatus());
            stmt.setInt(7, task.getUserId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Task added successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error inserting task: " + e.getMessage());
        }
    }

    @Override
    public Task read(int id) {
        String sql = "SELECT * FROM Task WHERE id = ?";
        Task task = null;

        try (Connection conn = model.DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(LocalDateTime.parse(rs.getString("createdAt")));
                task.setUpdatedAt(LocalDateTime.parse(rs.getString("updatedAt")));
                task.setDueAt(LocalDateTime.parse(rs.getString("dueAt")));
                task.setStatus(rs.getString("status"));
                task.setUserId(rs.getInt("userId"));
            }

        } catch (Exception e) {
            System.out.println("Error reading task: " + e.getMessage());
        }

        return task;
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, updatedAt = ?, dueAt = ?, status = ? " +
                "WHERE id = ?";

        try (Connection conn = model.DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getUpdatedAt().toString());
            stmt.setString(4, task.getDueAt().toString());
            stmt.setString(5, task.getStatus());
            stmt.setInt(6, task.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Task updated successfully.");
            } else {
                System.out.println("No task found with that ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = model.DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Task deleted successfully.");
            } else {
                System.out.println("No task found with that ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }

    public List<Task> getTasksByUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE userId = ?";

        try (Connection conn = model.DataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setCreatedAt(LocalDateTime.parse(rs.getString("createdAt")));
                task.setUpdatedAt(LocalDateTime.parse(rs.getString("updatedAt")));
                task.setDueAt(LocalDateTime.parse(rs.getString("dueAt")));
                task.setStatus(rs.getString("status"));
                task.setUserId(rs.getInt("userId"));

                tasks.add(task);
            }

        } catch (Exception e) {
            System.out.println("Error fetching tasks: " + e.getMessage());
        }

        return tasks;
    }
}
