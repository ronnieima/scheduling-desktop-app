package kaito.software2.utilities;

import javafx.scene.control.Alert;

import java.time.LocalDate;

public interface Validate {

     static Alert createAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }

    static void popupError(int alertId) {
        switch (alertId){
            case 1: // checkDate error
                Alert checkDateAlert = createAlert(Alert.AlertType.ERROR, "Error", "Start date must be before end date.");
                checkDateAlert.showAndWait();
                break;
            case 2: // username empty
                Alert emptyError = createAlert(Alert.AlertType.ERROR, "Error", "Please fill in the empty field(s).");
                emptyError.showAndWait();
                break;
            case 3: // no item selected
                Alert noItemSelected = createAlert(Alert.AlertType.ERROR, "Error", "Please select an item.");
                noItemSelected.showAndWait();
                break;
            case 4: // customer still has appointments when being deleted
                Alert customerHasAppts = createAlert(Alert.AlertType.ERROR, "Error", "Please delete all appointments before deleting.");
                customerHasAppts.showAndWait();
                break;
        }
    }

    //TODO Functions that take a username and change it to UserID & vice versa.
    default void convertUsername(String username) {
        try {
            // iterate through each username in DB
                // if username.equals(DB username)
                    // return username's ID

        } catch(Exception e) {
            popupError(2);
        }
    }

    default boolean checkDate(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            popupError(1);
            return false;
        }
        return true;
    }
}
