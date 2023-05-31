package kaito.software2.model;

/**
 * Model class for a user
 */
public class User {

    private int userId;
    private String userName;
    private String password;

    /**
     * Constructor class for a user
     * @param userId  User's Id
     * @param userName User's username
     * @param password User's password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Overridden method which displays its attributes as a String
     * @return User's attributes as a String
     */
    @Override
    public String toString() {
        return userName + " | ID: " + userId;
    }

    /**
     * Getter for UserID
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Getter for username
     * @return Username ID
     */
    public String getUserName() {
        return userName;
    }

}
