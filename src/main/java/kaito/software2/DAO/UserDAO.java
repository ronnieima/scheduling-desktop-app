package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User>{

    @Override
    public User get(int id) throws SQLException {
        String sql = "SELECT * FROM users";
        User newUser = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int newId = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");
            newUser = new User(newId, name, password);
        }
        return newUser;
    }

    public ObservableList<User> getAll() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");
            User newUser = new User(id, name, password);
            allUsers.add(newUser);
        }
        return allUsers;
    }

    @Override
    public int save(User user) throws SQLException {
        return 0;
    }

    @Override
    public int insert(User user) throws SQLException {
        return 0;
    }

    @Override
    public int update(User user) throws SQLException {
        return 0;
    }

    @Override
    public int delete(User user) throws SQLException {
        return 0;
    }

}
