package controller;

import model.Task;
import model.ManagerModel;
import view.View;
import java.util.List;

public class TasksController {
    private ManagerModel model;
    private View view;

    public TasksController(ManagerModel model, View view) {
        this.model = model;
        this.view = view;
    }

    public void addTask(String title, String description, int userId) {
        if (title == null || title.trim().isEmpty()) {
            view.showMessage("Error: Task title cannot be empty");
            return;
        }
        
        model.addTask(title, description, userId);
        view.showMessage("Task added successfully");
    }

    public void viewAllTasks(int userId) {
        List<Task> tasks = model.getAllTasks(userId);
        view.showTaskList(tasks);
    }

    public void updateTask(int id, String title, String description, int userId) {
        if (title == null || title.trim().isEmpty()) {
            view.showMessage("Error: Task title cannot be empty");
            return;
        }
        
        boolean updated = model.updateTask(id, title, description, userId);
        if (updated) {
            view.showMessage("Task updated successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }

    public void deleteTask(int id, int userId) {
        boolean deleted = model.deleteTask(id, userId);
        if (deleted) {
            view.showMessage("Task deleted successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }
    
    public void markTaskAsDone(int id, int userId) {
        boolean updated = model.updateTaskStatus(id, "done", userId);
        if (updated) {
            view.showMessage("Task marked as done successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }
    
    public void markTaskAsNotDone(int id, int userId) {
        boolean updated = model.updateTaskStatus(id, "pending", userId);
        if (updated) {
            view.showMessage("Task marked as not done successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }
}
