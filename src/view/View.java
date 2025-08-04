package view;

import java.util.List;
import model.Task;


public class View {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_WHITE_BG = "\u001B[47m";
    private static final String ANSI_BLACK = "\u001B[30m";
    
    public void printHeader(String title) {
        System.out.println();
        System.out.println(ANSI_CYAN + "╔" + "═".repeat(78) + "╗" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║" + ANSI_BOLD + ANSI_WHITE_BG + ANSI_BLACK + 
                         String.format(" %-76s ", title) + 
                         ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "╠" + "═".repeat(78) + "╣" + ANSI_RESET);
    }
    
    public void printFooter() {
        System.out.println(ANSI_CYAN + "╚" + "═".repeat(78) + "╝" + ANSI_RESET);
    }
    
    public void printLine(String line) {
        System.out.println(ANSI_CYAN + "║ " + ANSI_RESET + line);
    }
    
    public void printDivider() {
        System.out.println(ANSI_CYAN + "╠" + "─".repeat(78) + "╣" + ANSI_RESET);
    }
    
    public void showMainMenu(String username) {
        printHeader("TASK MANAGER - Welcome, " + username + "!");
        printLine(ANSI_BOLD + "MAIN MENU" + ANSI_RESET);
        printDivider();
        printLine(ANSI_BOLD + "1." + ANSI_RESET + " Add Task");
        printLine(ANSI_BOLD + "2." + ANSI_RESET + " View All Tasks");
        printLine(ANSI_BOLD + "3." + ANSI_RESET + " Update Task");
        printLine(ANSI_BOLD + "4." + ANSI_RESET + " Delete Task");
        printLine(ANSI_BOLD + "5." + ANSI_RESET + " Mark Task as Done");
        printLine(ANSI_BOLD + "6." + ANSI_RESET + " Unmark Task as Done");
        printLine(ANSI_BOLD + "7." + ANSI_RESET + " User Management");
        printLine(ANSI_BOLD + "8." + ANSI_RESET + " Logout");
        printLine(ANSI_BOLD + "9." + ANSI_RESET + " Exit");
        printDivider();
    }
    
    public void showLoginMenu() {
        printHeader("TASK MANAGER - LOGIN");
        printLine(ANSI_BOLD + "1." + ANSI_RESET + " Login");
        printLine(ANSI_BOLD + "2." + ANSI_RESET + " Register");
        printLine(ANSI_BOLD + "3." + ANSI_RESET + " Exit");
        printDivider();
    }
    
    public void showUserManagementMenu() {
        printHeader("USER MANAGEMENT");
        printLine(ANSI_BOLD + "1." + ANSI_RESET + " List All Users");
        printLine(ANSI_BOLD + "2." + ANSI_RESET + " Add New User");
        printLine(ANSI_BOLD + "3." + ANSI_RESET + " Update User");
        printLine(ANSI_BOLD + "4." + ANSI_RESET + " Delete User");
        printLine(ANSI_BOLD + "5." + ANSI_RESET + " Back to Main Menu");
        printDivider();
        System.out.print(ANSI_CYAN + "║ " + ANSI_BOLD + "Enter your choice: " + ANSI_RESET);
    }

    public void showTaskList(List<Task> tasks) {
        printHeader("TASK LIST");
        
        if (tasks == null || tasks.isEmpty()) {
            printLine(ANSI_YELLOW + "No tasks found." + ANSI_RESET);
            printFooter();
            return;
        }
        
        // Table header
        printLine(String.format("%-5s %-25s %-40s %s", 
            ANSI_BOLD + "ID" + ANSI_RESET, 
            ANSI_BOLD + "TITLE" + ANSI_RESET, 
            ANSI_BOLD + "DESCRIPTION" + ANSI_RESET, 
            ANSI_BOLD + "STATUS" + ANSI_RESET));
        printDivider();
        
        for (Task task : tasks) {
            String status = "pending".equals(task.getStatus()) ? 
                ANSI_YELLOW + "[ PENDING ]" + ANSI_RESET : 
                ANSI_GREEN + "[  DONE   ]" + ANSI_RESET;
                
            String description = task.getDescription();
            if (description != null && description.length() > 38) {
                description = description.substring(0, 35) + "...";
            }
            
            printLine(String.format("%-5d %-25s %-40s %s", 
                task.getId(), 
                task.getTitle().length() > 23 ? task.getTitle().substring(0, 20) + "..." : task.getTitle(),
                description != null ? description : "",
                status));
        }
        printFooter();
    }


    public void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
        }
    }
    
    public void showMessage(String message) {
        if (message.toLowerCase().contains("error")) {
            printLine(ANSI_YELLOW + "⚠ " + message + ANSI_RESET);
        } else if (message.toLowerCase().contains("success") || message.toLowerCase().contains("added") || 
                  message.toLowerCase().contains("updated") || message.toLowerCase().contains("deleted")) {
            printLine(ANSI_GREEN + "✓ " + message + ANSI_RESET);
        } else {
            printLine(message);
        }
        printFooter();
    }
}