package kaito.software2.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.DAO.UserDAO;
import kaito.software2.Main;
import kaito.software2.model.User;
import kaito.software2.utilities.Nav;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for the login screen
 */
public class LoginScreenController extends UserDAO implements Initializable, Nav {

    public static ResourceBundle enBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
    public static ResourceBundle frBundle = ResourceBundle.getBundle("Lang", Locale.FRENCH);
    public Button loginButton;
    public TextField usernameField;
    public Label loginLabel;
    public PasswordField passwordField;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label timezone;
    public Label timezoneLabel;
    public static boolean english;
    public static boolean french;

    /**
     * Initialize method that sets the local system's language and time zone.
     * LAMBDA : I used a lambda to initiate a login when pressing the ENTER key after entering the password.
     *                  This makes it so that the user does not have to move the mouse to login.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale currentLocale = Locale.getDefault();
        timezone.setText(String.valueOf(ZoneId.systemDefault()));
        setLanguage(currentLocale.getLanguage());

        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    login();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sets the language of the login screen
     *
     */
    public void setLanguage(String language) {
        if (language.equals("en")) {
            english = true;
            french = false;
            loginLabel.setText(enBundle.getString("login"));
            usernameLabel.setText(enBundle.getString("username"));
            usernameField.setPromptText(enBundle.getString("enterUsername"));
            passwordLabel.setText(enBundle.getString("password"));
            passwordField.setPromptText(enBundle.getString("enterPassword"));
            loginButton.setText(enBundle.getString("login"));
            Main.getStage().setTitle(enBundle.getString("login"));
        } else if (language.equals("fr")) {
            french = true;
            english = false;
            loginLabel.setText(frBundle.getString("login"));
            usernameLabel.setText(frBundle.getString("username"));
            usernameField.setPromptText(frBundle.getString("enterUsername"));
            passwordLabel.setText(frBundle.getString("password"));
            passwordField.setPromptText(frBundle.getString("enterPassword"));
            loginButton.setText(frBundle.getString("login"));
            timezoneLabel.setText(frBundle.getString("timezone"));
            Main.getStage().setTitle(frBundle.getString("login"));
        }
    }

    /**
     * Switches to the main screen if user enters the correct login credentials.
     * It also writes to a the activity log whenever a user logs in or fails a login.
     */
    public void login() throws IOException, SQLException {

        FileWriter writer = new FileWriter("src/login_activity.txt", true);

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = checkLogin(username.trim(), password);
        if (user != null) {
            String successLog = "User " + user.getUserName() + " successfully logged in at " + LocalDateTime.now(ZoneId.of("UTC")) + " UTC\n";
            writer.append(successLog);
            switchScene("view/appointment-screen.fxml");
            appointmentDAO.checkUpcomingAppointments(user);
        } else {
            String failLog = "User " + username + " gave invalid login at " + LocalDateTime.now(ZoneId.of("UTC")) + " UTC\n";
            writer.append(failLog);
        }
        writer.close();
    }
}