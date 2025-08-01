package model;
// User class for holding basic user info
//(Done by Ephrata Walelign)
public class User {
    private int id;
    private String username;
    private String password;

    // Empty constructor (needed sometimes when we dont want to st right away)
    public User() {
    }

   // Constructor to create a user with id, username and password
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters and setters


    public int getId() {
        return id;
    }


    public String getUsername() {

        return username;
    }


    public String getPassword() {
        return password;
    }


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
    // I made this one just to shoe user info in a simple way without showing the password
    public String toDisplayString() {
        return "User ID: " + ", Username: " + username;
    }
    @override
    public String toString() {
        return "User{Id=" + id + ", username= " + username + ", password= " + password + "};
    }