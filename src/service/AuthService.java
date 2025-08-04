package service;

import model.User;
import model.ManagerModel;
import java.util.Scanner;

public class AuthService {
    private final ManagerModel model;
    private User currentUser;
    private final Scanner scanner;

    public AuthService(ManagerModel model, Scanner scanner) {
        this.model = model;
        this.scanner = scanner;
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public boolean login() {
        System.out.print("\nEnter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User user = model.authenticateUser(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("\nLogin successful! Welcome, " + username + "!");
            return true;
        } else {
            System.out.println("\nInvalid username or password. Please try again.");
            return false;
        }
    }

    public void logout() {
        if (currentUser != null) {
            System.out.println("\nGoodbye, " + currentUser.getUsername() + "!");
            currentUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public void showLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }
}
