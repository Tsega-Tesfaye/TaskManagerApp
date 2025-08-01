package model;

public class User {
    private int id;
    private String username;
    private String password;

    // Empty constructor (needed sometimes when we dont want to start right away)
    public User() {
    }
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

    public void setUsername(String username) {

        this.username = username;
    }

    public void setPassword(String password) {

        this.password = password;
    }
    // I made this one just to show user info in a simple way without showing the password
    public String toDisplayString() {
        return "User ID: " + ", Username: " + username;
    }
    @override
    public String toString() {
        return "User{Id=" + id + ", username= " + username + ", password= " + password + "};
    }