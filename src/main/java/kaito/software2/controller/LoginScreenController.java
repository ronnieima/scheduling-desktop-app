package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.DAO.UserDAO;
import kaito.software2.Main;
import kaito.software2.model.User;
import kaito.software2.utilities.Nav;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ERROR: I thought jp was the langauge code but i discovered it was ja
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
    public static boolean english = true;
    public static boolean french = false;


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
     * @throws IOException
     */
    public void login() throws IOException, SQLException {

        FileWriter writer = new FileWriter("src/login_activity.txt", true);

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = checkLogin(username, password);
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