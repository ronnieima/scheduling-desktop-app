module kaito.software2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens kaito.software2 to javafx.fxml;
    exports kaito.software2;
    exports kaito.software2.controller;
    opens kaito.software2.controller to javafx.fxml;
    exports kaito.software2.model;
    opens kaito.software2.model to javafx.fxml;


}