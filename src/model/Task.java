package model;

import java.time.LocalDateTime;
public class Task implements Model {
    private int id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueAt;
    private String status;
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
   @override
    public int getId() {
        return id;
        @override
       public void setId(int id) {
            this.id = id;
            public String getTitle() {
                return title;
                    public void setTitle(String title) {
                        this.title= title;
                        public string getDescription() {
                            return description;
                            public void setDescription(String description) {
                                this.description = description;
                                public LocalDateTime getCreatedAt()
                           return created;
                           public void setCreatedAt(LocalDateTime createdAt) {
                               this.createdAt = createdAt;
                           }
                           public LocalDateTime getUpdatedAt() {
                               return updatedAt;
                           }
                           public void setUpdatedAt(LocalDateTime updatedAt) {
                               this.updatedAt = updatedAt;
                           }
                           public LocalDateTime getDueAt() {
                               return dueAt;
                           }
                           public void setDueAt(LocalDateTime dueAt) {
                               this.dueAt = dueAt;
                           }
                           public String getStatus() {
                               return status;
                           }
                           public void setStatus(String status) {
                               this.status = status;
                           }
                           public int getUserId() {
                               return userId;
                           }
                           public void setUserId(int userId) {
                               this.userId = userId;
                           }
                           public String toDisplayString() {
                               return "Task: " + title + ", Due: " + dueAt.toLocalDate() + ", Status: " + status;
                           }
                           @Override
                           public String toString() {
                               return "Task{" +
                                       "id=" + id +
                                       ", title='" + title + '\'' +
                                       ", description='" + description + '\'' +
                                       ", createdAt=" + createdAt +
                                       ", updatedAt=" + updatedAt +
                                       ", dueAt=" + dueAt +
                                       ", status='" + status + '\'' +
                                       ", userId=" + userId +
                                       '}';
                           }