package kaito.software2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import kaito.software2.DAO.JDBC;
import kaito.software2.utilities.Validate;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import static kaito.software2.controller.LoginScreenController.*;


/**
 * Main class which calls the functions that opens the database, launches the stage, and closes the database on exit.
 */
public class Main extends Application implements Validate{

    private static Stage stage;
    public static final LocalTime OPENING_TIME = LocalTime.of(8, 00);
    public static final LocalTime CLOSING_TIME = LocalTime.of(22, 00);
    /**
     * Creates the stage for all of the scenes to be put in.
     * LAMBDA - I added a functionality that prompts that user if they would like to exit after pressing the
     *          exit button on the window. This would prevent the user from accidentally closing out of the progran.
     */
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            Alert exitConf;
            if(french) {
                exitConf = Validate.createAlert(Alert.AlertType.CONFIRMATION, "Sortie", "Voulez-vous vraiment quitter ?");
            } else {
                exitConf = Validate.createAlert(Alert.AlertType.CONFIRMATION, "Exit", "Are you sure you would like to exit?");
            }

            if (exitConf.showAndWait().get() == ButtonType.OK) {
                Platform.exit();
            } else {
                windowEvent.consume();
            }
        });
    }
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();




    }

    public static Stage getStage() {
        return stage;
    }

}


