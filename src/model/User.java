package model;

/**
 * The User class represents a user entity in the system.
 * Students: This class should be used to store and transfer user data between
 * the application and the database.
 * Each field corresponds to a column in the users table in the database.
 */
public class User implements Model {
    // Unique identifier for the user (primary key in the database)
    private int id;
    // The username chosen by the user
    private String username;
    // The user's password (should be stored securely in a real application)
    private String password;

    /**
     * Default constructor. Creates an empty User object.
     * Students: Use this when you need to instantiate a User without setting fields
     * immediately.
     */
    public User() {
    }

    /**
     * Parameterized constructor. Creates a User with the given id, username, and
     * password.
     * Students: Use this to quickly create a User object with all fields set.
     * 
     * @param id       The user's unique ID.
     * @param username The user's username.
     * @param password The user's password.
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    /**
     * Gets the user's ID.
     * 
     * @return The user's unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the user's username.
     * 
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user's password.
     * 
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's ID.
     * 
     * @param id The new ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the user's username.
     * 
     * @param username The new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user's password.
     * 
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}