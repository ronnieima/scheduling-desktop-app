package kaito.software2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User {

    private int userId;
    private String userName;
    private String password;
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return userName + " | ID: " + userId;
    }

    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ObservableList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}