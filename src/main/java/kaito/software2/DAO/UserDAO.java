package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static kaito.software2.utilities.Validate.popupError;
/**
 * DAO class for users
 */
public class UserDAO implements DAO<User>{

    /**
     * Checks a given username and password with the database for authentication for login
     * @param username Given username
     * @param password Give
     * @return A user that has been authenticated. Returns null if no user found.
     * @throws SQLException
     */
    public User checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            User authUser = get(rs.getInt("User_ID"));
            return authUser;
        }
        popupError(8);
        return null;
    }
    /**
     * Gets an user given an contact ID
     * @param id ID to check
     * @return User object
     */
    @Override
    public User get(int id) throws SQLException {
        String sql = "SELECT * FROM users";
        User newUser = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int newId = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");
            newUser = new User(newId, name, password);
        }
        return newUser;
    }

    /**
     * Gets a list of all users in the database
     * @return  a list of all users in the database
     */
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
            /**
     * Saves current user - Unused
     */
    @Override
    public int save(User user) throws SQLException {
        return 0;
    }
        /**
     * insert current user - Unused
     */
    @Override
    public int insert(User user) throws SQLException {
        return 0;
    }
        /**
     * update current user - Unused
     */
    @Override
    public int update(User user) throws SQLException {
        return 0;
    }
        /**
     * delete current user - Unused
     */
    @Override
    public int delete(User user) throws SQLException {
        return 0;
    }
}
