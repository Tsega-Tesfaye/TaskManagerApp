package controller;

import model.Task;
import java.util.List;

/**
 * Controller for managing Task-related actions.
 */
public class TasksController implements Controller<Task> {

    @Override
    public void create(Task task) {
        // Insert task into database
    }

    @Override
    public Task read(int id) {
        // Get task by id
        return null;
    }

    @Override
    public void update(Task task) {
        // Update task
    }

    @Override
    public void delete(int id) {
        // Delete task by id
    }

    public List<Task> getTasksByUser(int userId) {
        // Fetch all tasks for a given user
        return null;
    }
}