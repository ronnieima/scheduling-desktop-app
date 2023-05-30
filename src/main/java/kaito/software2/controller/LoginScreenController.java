package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import kaito.software2.Main;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ERROR: I thought jp was the langauge code but i discovered it was ja
 */
public class LoginScreenController implements Initializable, Nav {

    public Button loginButton;
    public TextField usernameField;
    public Label loginLabel;
    public ChoiceBox<String> languageChoiceBox;
    public PasswordField passwordField;
    public Label passwordLabel;
    public Label usernameLabel;
    public Label languageLabel;
    private String[] languageList = {"ENGLISH", "FRENCH"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Login");
        languageChoiceBox.getItems().addAll(languageList);
        languageChoiceBox.setValue("ENGLISH");
        // Listener for when user changes the
        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> setLanguage(newValue));
    }

    public void setLanguage(String language) {
        if (language.equals("ENGLISH")) {
            Main.english = true;
//            Validation.japanese = false;
            loginLabel.setText(Main.enBundle.getString("login"));
            usernameLabel.setText(Main.enBundle.getString("username"));
            usernameField.setPromptText(Main.enBundle.getString("enterUsername"));
            passwordLabel.setText(Main.enBundle.getString("password"));
            passwordField.setPromptText(Main.enBundle.getString("enterPassword"));
            languageLabel.setText(Main.enBundle.getString("language"));
            loginButton.setText(Main.enBundle.getString("login"));
            Main.getStage().setTitle(Main.enBundle.getString("login"));
//        } else if (language.equals("JAPANESE")) {
//            Validation.japanese = true;
//            Validation.english = false;
//            loginLabel.setText(Validation.jpBundle.getString("login"));
//            usernameLabel.setText(Validation.jpBundle.getString("username"));
//            usernameField.setPromptText(Validation.jpBundle.getString("enterUsername"));
//            passwordLabel.setText(Validation.jpBundle.getString("password"));
//            passwordField.setPromptText(Validation.jpBundle.getString("enterPassword"));
//            languageLabel.setText(Validation.jpBundle.getString("language"));
//            loginButton.setText(Validation.jpBundle.getString("login"));
//            Main.getStage().setTitle(Validation.jpBundle.getString("login"));
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
    }

}