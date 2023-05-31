package kaito.software2.DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Interface which sets the framework for all DAO classes
 * @param <T> Generic object
 */
public interface DAO<T> {
    T get(int id) throws SQLException;

    /**
     * Get all method
     */
    default ObservableList<T> getAll() throws SQLException {
        return null;
    }

    /**
     * Save method
     */
    int save(T t) throws SQLException;

    /**
     * Insert Method
     */
    int insert(T t) throws SQLException;

    /**
     * Update method

     */
    int update(T t) throws SQLException;

    /**
     * Delete method
     */
    int delete(T t) throws SQLException;
}
