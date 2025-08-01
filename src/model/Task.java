package model;

import java.time.LocalDateTime;

/**
 * Represents a Task that belongs to a user.
 */
public class Task implements Model {
    private int id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueAt;
    private String status; // "pending", "done"
    private int userId;

    public Task() {}

    public Task(int id, String title, String description, LocalDateTime createdAt,
                LocalDateTime updatedAt, LocalDateTime dueAt, String status, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dueAt = dueAt;
        this.status = status;
        this.userId = userId;
    }

    // Getters and setters ...

    // Method implementation(implement all listed in the Model interface)
}