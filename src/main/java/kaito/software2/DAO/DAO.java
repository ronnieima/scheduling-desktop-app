package kaito.software2.DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface DAO<T> {
    T get(int id) throws SQLException;

    default ObservableList<T> getAll() throws SQLException {
        return null;
    }

    int save(T t) throws SQLException;

    int insert(T t) throws SQLException;

    int update(T t) throws SQLException;

    int delete(T t) throws SQLException;
}
