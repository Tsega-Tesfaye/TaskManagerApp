package controller;

import model.Task;
import model.TaskManagerModel;
import view.View;
import java.util.List;

public class TasksController {
    private TaskManagerModel model;
    private View view;

    public TasksController(TaskManagerModel model, View view) {
        this.model = model;
        this.view = view;
    }

    public void addTask(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            view.showMessage("Error: Task title cannot be empty");
            return;
        }
        
        model.addTask(title, description);
        view.showMessage("Task added successfully");
    }

    public void viewAllTasks() {
        List<Task> tasks = model.getAllTasks();
        view.showTaskList(tasks);
    }

    public void updateTask(int id, String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            view.showMessage("Error: Task title cannot be empty");
            return;
        }
        
        boolean updated = model.updateTask(id, title, description);
        if (updated) {
            view.showMessage("Task updated successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }

    public void deleteTask(int id) {
        boolean deleted = model.deleteTask(id);
        if (deleted) {
            view.showMessage("Task deleted successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }
    
    public void markTaskAsDone(int id) {
        boolean updated = model.updateTaskStatus(id, "done");
        if (updated) {
            view.showMessage("Task marked as done successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }
    
    public void unmarkTaskAsDone(int id) {
        boolean updated = model.updateTaskStatus(id, "pending");
        if (updated) {
            view.showMessage("Task unmarked as done successfully");
        } else {
            view.showMessage("Error: Task not found with ID: " + id);
        }
    }
}