package app;

import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;
import controller.TasksController;
import controller.UsersController;
import model.ManagerModel;
import model.Task;
import model.User;
import service.AuthService;
import model.DataSource;
import view.View;


public class TaskManagerApp {
    // ANSI color codes 
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_BOLD = "\u001B[1m";
    protected static final String ANSI_CYAN = "\u001B[36m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_YELLOW = "\u001B[33m";
    protected static final String ANSI_WHITE_BG = "\u001B[47m";
    protected static final String ANSI_BLACK = "\u001B[30m";
    private static void showLoginMenu(AuthService authService, View view, Scanner scanner, ManagerModel model) {
        boolean loggedIn = false;
        while (!loggedIn) {
            view.clearScreen();
            view.showLoginMenu();
            
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                view.showMessage("Invalid choice. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1: // Login
                    loggedIn = authService.login();
                    if (!loggedIn) {
                        System.out.println("\nPress Enter to continue...");
                        scanner.nextLine();
                    }
                    break;
                case 2: // Register
                    registerNewUser(authService, view, scanner, model);
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 3: // Exit
                    System.out.println("\nGoodbye!");
                    System.exit(0);
                    break;
                default:
                    view.showMessage("Invalid choice. Please try again.");
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
            }
        }
    }
    
    private static void registerNewUser(AuthService authService, View view, Scanner scanner, ManagerModel model) {
        view.clearScreen();
        view.printHeader("REGISTER NEW USER");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        try {
            // Create a new user
            User newUser = new User(0, username, password);
            
            // Create a new UsersController with the existing model
            UsersController usersController = new UsersController(model, view);
            
            // Save the user to the database
            usersController.create(newUser);
            
            System.out.println("\nUser registered successfully! You can now log in.");
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void handleUserManagement(UsersController usersController, View view, Scanner scanner) {
        boolean backToMain = false;
        
        while (!backToMain) {
            view.clearScreen();
            view.showUserManagementMenu();
            
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                view.showMessage("Invalid choice. Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1: // List all users
                    listAllUsers(usersController, view);
                    break;
                case 2: // Add new user
                    addNewUser(usersController, view, scanner);
                    break;
                case 3: // Update user
                    updateUser(usersController, view, scanner);
                    break;
                case 4: // Delete user
                    deleteUser(usersController, view, scanner);
                    break;
                case 5: // Back to main menu
                    backToMain = true;
                    continue;
                default:
                    view.showMessage("Invalid choice. Please try again.");
            }
            
            if (!backToMain) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    private static void listAllUsers(UsersController usersController, View view) {
        view.clearScreen();
        view.printHeader("ALL USERS");
        
        List<User> users = usersController.getAllUsers();
        if (users.isEmpty()) {
            view.showMessage("No users found.");
            return;
        }
        
        // Print table header
        System.out.println(String.format("%-5s %-20s", "ID", "USERNAME"));
        System.out.println("-".repeat(30));
        
        // Print each user
        for (User user : users) {
            System.out.println(String.format("%-5d %-20s", user.getId(), user.getUsername()));
        }
    }
    
    private static void addNewUser(UsersController usersController, View view, Scanner scanner) {
        view.clearScreen();
        view.printHeader("ADD NEW USER");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        try {
            User newUser = new User(0, username, password);
            usersController.create(newUser);
            view.showMessage("User created successfully!");
        } catch (Exception e) {
            view.showMessage("Error creating user: " + e.getMessage());
        }
    }
    
    private static void updateUser(UsersController usersController, View view, Scanner scanner) {
        view.clearScreen();
        view.printHeader("UPDATE USER");
        
        // First, list all users
        List<User> users = usersController.getAllUsers();
        if (users.isEmpty()) {
            view.showMessage("No users found to update.");
            return;
        }
        
        // Display users
        System.out.println("Select user to update:");
        for (User user : users) {
            System.out.println(String.format("%d. %s", user.getId(), user.getUsername()));
        }
        
        System.out.print("\nEnter user ID to update: ");
        try {
            int userId = Integer.parseInt(scanner.nextLine().trim());
            
            // Find the user
            User userToUpdate = users.stream()
                .filter(u -> u.getId() == userId)
                .findFirst()
                .orElse(null);
                
            if (userToUpdate == null) {
                view.showMessage("User not found with ID: " + userId);
                return;
            }
            
            // Get updated information
            System.out.println("\nLeave field blank to keep current value.");
            
            System.out.print(String.format("Username [%s]: ", userToUpdate.getUsername()));
            String newUsername = scanner.nextLine().trim();
            if (!newUsername.isEmpty()) {
                userToUpdate.setUsername(newUsername);
            }
            
            System.out.print("New password (leave blank to keep current): ");
            String newPassword = scanner.nextLine().trim();
            if (!newPassword.isEmpty()) {
                userToUpdate.setPassword(newPassword);
            }
            
            // Update the user
            usersController.update(userToUpdate);
            view.showMessage("User updated successfully!");
            
        } catch (NumberFormatException e) {
            view.showMessage("Invalid user ID. Please enter a number.");
        } catch (Exception e) {
            view.showMessage("Error updating user: " + e.getMessage());
        }
    }
    
    private static void deleteUser(UsersController usersController, View view, Scanner scanner) {
        view.clearScreen();
        view.printHeader("DELETE USER");
        
        // First, list all users
        List<User> users = usersController.getAllUsers();
        if (users.isEmpty()) {
            view.showMessage("No users found to delete.");
            return;
        }
        
        // Display users
        System.out.println("Select user to delete:");
        for (User user : users) {
            System.out.println(String.format("%d. %s", user.getId(), user.getUsername()));
        }
        
        System.out.print("\nEnter user ID to delete: ");
        try {
            int userId = Integer.parseInt(scanner.nextLine().trim());
            
            // Confirm deletion
            System.out.print("\nAre you sure you want to delete this user? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("yes") || confirmation.equals("y")) {
                usersController.delete(userId);
                view.showMessage("User deleted successfully!");
            } else {
                view.showMessage("User deletion cancelled.");
            }
            
        } catch (NumberFormatException e) {
            view.showMessage("Invalid user ID. Please enter a number.");
        } catch (Exception e) {
            view.showMessage("Error deleting user: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        try {
            DataSource.initialize();
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        ManagerModel model = new ManagerModel();
        Scanner scanner = new Scanner(System.in);
        View view = new View();
        TasksController tasksController = new TasksController(model, view);
        UsersController usersController = new UsersController(model, view);
        AuthService authService = new AuthService(model, scanner);
        
        view.showMessage(ANSI_BOLD + "Welcome to the Task Manager App!" + ANSI_RESET);
        
        // Main application loop
        boolean running = true;
        int currentUserId = -1;
        
        while (running) {
            // Show login menu if not authenticated
            if (!authService.isAuthenticated()) {
                showLoginMenu(authService, view, scanner, model);
                if (authService.isAuthenticated()) {
                    currentUserId = authService.getCurrentUser().getId();
                } else {
                    continue;
                }
            }
            
            // Show main menu for authenticated users
            view.clearScreen();
            view.showMainMenu(authService.getCurrentUser().getUsername());
            
            int choice = 0;
            boolean validChoice = false;
            while (!validChoice) {
                try {
                    System.out.print(ANSI_CYAN + "║ " + ANSI_BOLD + "Enter your choice: " + ANSI_RESET);
                    choice = Integer.parseInt(scanner.nextLine());
                    validChoice = true;
                } catch (NumberFormatException e) {
                    view.showMessage("Invalid input. Please enter a number.");
                }
            }
            
            // Ensure user is authenticated for task management
            if (!authService.isAuthenticated()) {
                view.showMessage("You must be logged in to perform this action.");
                continue;
            }
            
            switch (choice) {
                case 1: // Add new task
                    view.printHeader("ADD NEW TASK");
                    view.printLine("Enter task details:");
                    view.printDivider();
                    
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Title: ");
                    String title = scanner.nextLine().trim();
                    
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Description: ");
                    String description = scanner.nextLine().trim();
                    
                    tasksController.addTask(title, description, currentUserId);
                    waitForUserInput(scanner);
                    break;
                    
                case 2: // View all tasks
                    view.clearScreen();
                    tasksController.viewAllTasks(currentUserId);
                    waitForUserInput(scanner);
                    break;
                    
                case 3: // Update task
                    view.printHeader("UPDATE TASK");
                    view.printLine("Enter task ID to update:");
                    view.printDivider();
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                    
                    int updateId = 0;
                    while (true) {
                        try {
                            String input = scanner.nextLine();
                            updateId = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            view.showMessage("Invalid task ID. Please enter a number.");
                            System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                        }
                    }                  
                    // Get current task to show existing values
                    Task currentTask = model.getTaskById(updateId, currentUserId);
                    if (currentTask == null) {
                        view.showMessage("Error: Task not found with ID: " + updateId);
                        break;
                    }
                    
                    // Show current task details
                    view.printHeader("UPDATE TASK #" + updateId);
                    view.printLine("Current title: " + ANSI_BOLD + currentTask.getTitle() + ANSI_RESET);
                    view.printLine("Current description: " + (currentTask.getDescription() != null ? currentTask.getDescription() : ""));
                    view.printDivider();
                    
                    // Get new title (press Enter to keep current)
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "New title [" + currentTask.getTitle() + "]: ");
                    String newTitle = scanner.nextLine();
                    if (newTitle.trim().isEmpty()) {
                        newTitle = currentTask.getTitle();
                    }
                    
                    // Get new description (press Enter to keep current)
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "New description [" + 
                                   (currentTask.getDescription() != null ? currentTask.getDescription() : "") + 
                                   "]: ");
                    String newDesc = scanner.nextLine();
                    if (newDesc.trim().isEmpty()) {
                        newDesc = currentTask.getDescription();
                    }
                    
                    // Only update if at least one field was changed
                    if (!newTitle.equals(currentTask.getTitle()) || 
                        (newDesc != null && !newDesc.equals(currentTask.getDescription()))) {
                        tasksController.updateTask(updateId, newTitle, newDesc, currentUserId);
                        view.clearScreen();
                        view.showMessage("Task updated successfully. Here's the updated task list:");
                        tasksController.viewAllTasks(currentUserId);
                    } else {
                        view.clearScreen();
                        view.showMessage("No changes detected. Task not updated.");
                        tasksController.viewAllTasks(currentUserId);
                    }
                    waitForUserInput(scanner);
                    break;
                    
                case 4:
                    view.printHeader("DELETE TASK");
                    view.printLine("Enter task ID to delete:");
                    view.printDivider();
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                    
                    int deleteId = 0;
                    while (true) {
                        try {
                            String input = scanner.nextLine();
                            deleteId = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            view.showMessage("Invalid task ID. Please enter a number.");
                            System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                        }
                    }
                    tasksController.deleteTask(deleteId, currentUserId);
                    waitForUserInput(scanner);
                    break;
                    
                case 5:
                    view.printHeader("MARK TASK AS DONE");
                    view.printLine("Enter task ID to mark as done:");
                    view.printDivider();
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                    
                    int doneId = 0;
                    while (true) {
                        try {
                            String input = scanner.nextLine();
                            doneId = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            view.showMessage("Invalid task ID. Please enter a number.");
                            System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                        }
                    }
                    tasksController.markTaskAsDone(doneId, currentUserId);
                    waitForUserInput(scanner);
                    break;
                    
                case 6:
                    view.printHeader("UNMARK TASK AS DONE");
                    view.printLine("Enter task ID to unmark as done:");
                    view.printDivider();
                    System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                    
                    int unmarkId = 0;
                    while (true) {
                        try {
                            String input = scanner.nextLine();
                            unmarkId = Integer.parseInt(input);
                            break;
                        } catch (NumberFormatException e) {
                            view.showMessage("Invalid task ID. Please enter a number.");
                            System.out.print(ANSI_CYAN + "║ " + ANSI_RESET + "Task ID: ");
                        }
                    }
                    tasksController.markTaskAsNotDone(unmarkId, currentUserId);
                    waitForUserInput(scanner);
                    break;
                    
                case 7: // User Management
                    handleUserManagement(usersController, view, scanner);
                    break;
                    
                case 8: // Logout
                    authService.logout();
                    view.showMessage("You have been logged out successfully.");
                    waitForUserInput(scanner);
                    break;
                    
                case 9: // Exit
                    view.clearScreen();
                    view.showMessage("Thank you for using Task Manager App!");
                    running = false;
                    return;
                    
                default:
                    view.showMessage("Invalid choice. Please try again.");
                    waitForUserInput(scanner);
                    break;
            }
        }
        
        System.out.println("Thank you for using Task Manager App!");
        scanner.close();
    }
    
    /**
     * Waits for the user to press Enter before continuing.
     * @param scanner The Scanner instance to use for input
     */
    private static void waitForUserInput(Scanner scanner) {
        System.out.print("\n" + ANSI_CYAN + "║ " + ANSI_RESET + "Press Enter to continue...");
        scanner.nextLine();
    }
}