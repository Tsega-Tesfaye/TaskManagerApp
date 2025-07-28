package controller;

import java.util.List;

import model.User;

/**
 * UsersController manages all actions related to User objects.
 * Each method should interact with the database using the DataSource class and
 * SQL queries.
 * Students should implement the logic for each method as described in the
 * comments below.
 */
public class UsersController implements Controller<User> {

    /**
     * Inserts a new user into the PostgreSQL database.
     * Students: Use DataSource.getConnection() to get a connection, then use a
     * PreparedStatement
     * to insert the user's data into the appropriate table. Handle SQL exceptions
     * and close resources.
     * 
     * @param user The User object to be added to the database.
     */
    @Override
    public void create(User user) {
        // TODO: Implement logic to insert user into the database using JDBC.
    }

    /**
     * Fetches a user from the database by their unique ID.
     * Students: Use a SELECT query with a WHERE clause on the user's ID. Map the
     * result set to a User object.
     * 
     * @param id The unique identifier of the user.
     * @return The User object if found, otherwise null.
     */
    @Override
    public User read(int id) {
        // TODO: Implement logic to fetch user by id from the database using JDBC.
        return null;
    }

    /**
     * Updates an existing user's information in the database.
     * Students: Use an UPDATE SQL statement to modify the user's data based on
     * their ID.
     * 
     * @param user The User object containing updated information.
     */
    @Override
    public void update(User user) {
        // TODO: Implement logic to update user info in the database using JDBC.
    }

    /**
     * Deletes a user from the database by their ID.
     * Students: Use a DELETE SQL statement with a WHERE clause on the user's ID.
     * 
     * @param id The unique identifier of the user to be deleted.
     */
    @Override
    public void delete(int id) {
        // TODO: Implement logic to delete user from the database using JDBC.
    }

    /**
     * Retrieves all users from the database.
     * Students: Use a SELECT * FROM users query, iterate through the ResultSet,
     * and create a list of User objects to return.
     * 
     * @return A list of all User objects in the database.
     */
    public List<User> getAllUsers() {
        // TODO: Implement logic to list all users from the database using JDBC.
        return null;
    }
}