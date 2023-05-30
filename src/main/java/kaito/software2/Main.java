package kaito.software2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kaito.software2.DAO.*;
import kaito.software2.model.Customer;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 *
 */
public class Main extends Application {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static boolean english = true;
    public static boolean french = false;
    public static ResourceBundle enBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
    public static ResourceBundle jpBundle = ResourceBundle.getBundle("Lang", Locale.FRENCH);
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/login-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1300, 800);
        stage.setResizable(false);
        stage.setTitle("Appointment Scheduler - Login");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
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

    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }
}


