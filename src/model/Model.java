package model;

/**
 * Model interface for representing data entities.
 * Acts as a marker or base for shared behaviors if extended in future.
 */
public interface Model {
    /**
     * Save the current model instance to the data store.
     */
    boolean save();

    /**
     * Delete the current model instance from the data store.
     */
    boolean delete();

    /**
     * Find a model instance by its unique identifier.
     * 
     * @param id the unique identifier
     * @return the found model instance or null if not found
     */
    static Model find(int id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Retrieve all model instances from the data store.
     * 
     * @return an array of all model instances
     */
    static Model[] All() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Query model instances by a custom condition.
     * 
     * @param condition the query condition (e.g., SQL WHERE clause)
     * @param params    parameters for the condition
     * @return an array of matching model instances
     */
    static Model[] where(String condition, Object... params) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Find a model instance by a specific field and value.
     * 
     * @param field the field name
     * @param value the value to search for
     * @return the found model instance or null if not found
     */
    static Model findBy(String field, Object value) {
        throw new UnsupportedOperationException("Not implemented");
    }
}