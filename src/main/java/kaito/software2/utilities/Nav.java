package kaito.software2.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import kaito.software2.Main;

import java.io.IOException;

/**
 * Navigation interface for moving around different scenes.
 */
public interface Nav {
    default void switchScene(String fxmlFile) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Main.class.getResource(fxmlFile)));
        Main.getStage().setScene(scene);
    }

    default void logout() throws IOException {
        Alert logout = Validate.createAlert(Alert.AlertType.CONFIRMATION, "Logout", "Are you sure you would like to log out?");
        if (logout.showAndWait().get() == ButtonType.OK) {
            switchScene("view/login-screen.fxml");
        }
    }
}
