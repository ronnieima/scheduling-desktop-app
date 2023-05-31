package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kaito.software2.Main;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static kaito.software2.utilities.Validate.createAlert;

/**
 * ERROR: I thought jp was the langauge code but i discovered it was ja
 */
public class LoginScreenController implements Initializable, Nav {

    public static ResourceBundle enBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
    public static ResourceBundle frBundle = ResourceBundle.getBundle("Lang", Locale.FRENCH);
    public Button loginButton;
    public TextField usernameField;
    public Label loginLabel;
    public ChoiceBox<String> languageChoiceBox;
    public PasswordField passwordField;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label languageLabel;
    public Label timezone;
    private String[] languageList = {"ENGLISH", "FRENCH"};
    public static boolean english = true;
    public static boolean french = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Login");
        languageChoiceBox.getItems().addAll(languageList);
        languageChoiceBox.setValue("ENGLISH");
        // Listener for when user changes the
        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> setLanguage(newValue));
        timezone.setText(String.valueOf(ZoneId.systemDefault()));
    }

    public void setLanguage(String language) {
        if (language.equals("ENGLISH")) {
            english = true;
            french = false;
            loginLabel.setText(enBundle.getString("login"));
            usernameLabel.setText(enBundle.getString("username"));
            usernameField.setPromptText(enBundle.getString("enterUsername"));
            passwordLabel.setText(enBundle.getString("password"));
            passwordField.setPromptText(enBundle.getString("enterPassword"));
            languageLabel.setText(enBundle.getString("language"));
            loginButton.setText(enBundle.getString("login"));
            Main.getStage().setTitle(enBundle.getString("login"));
        } else if (language.equals("FRENCH")) {
            french = true;
            english = false;
            loginLabel.setText(frBundle.getString("login"));
            usernameLabel.setText(frBundle.getString("username"));
            usernameField.setPromptText(frBundle.getString("enterUsername"));
            passwordLabel.setText(frBundle.getString("password"));
            passwordField.setPromptText(frBundle.getString("enterPassword"));
            languageLabel.setText(frBundle.getString("language"));
            loginButton.setText(frBundle.getString("login"));
            Main.getStage().setTitle(frBundle.getString("login"));
        }
    }

    /**
     * Switches to the main screen if TODO user and pass is correct.
     * @param actionEvent
     * @throws IOException
     */
    public void login(ActionEvent actionEvent) throws IOException {
        // get db of users and passses
        // check if username is valid
        // take username and compare it with password
        switchScene("view/appointment-screen.fxml");


            createAlert(Alert.AlertType.INFORMATION, "Upcoming Appointments", "You have an upcoming appointment within 15 minutes!");
    }

}