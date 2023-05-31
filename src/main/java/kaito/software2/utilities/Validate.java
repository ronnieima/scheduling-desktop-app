package kaito.software2.utilities;

import javafx.scene.control.Alert;
import kaito.software2.model.Appointment;
import static kaito.software2.controller.LoginScreenController.*;

import java.time.*;

import static kaito.software2.Main.CLOSING_TIME;
import static kaito.software2.Main.OPENING_TIME;

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
                Alert checkDateAlert = createAlert(Alert.AlertType.ERROR, "Error",
                        "Start date must be before end date.");
                checkDateAlert.showAndWait();
                break;
            case 2: // username empty
                Alert emptyError = createAlert(Alert.AlertType.ERROR, "Error",
                        "Please fill in the empty field(s).");
                emptyError.showAndWait();
                break;
            case 3: // no item selected
                Alert noItemSelected = createAlert(Alert.AlertType.ERROR, "Error",
                        "Please select an item.");
                noItemSelected.showAndWait();
                break;
            case 4: // customer still has appointments when being deleted
                Alert customerHasAppts = createAlert(Alert.AlertType.ERROR, "Error",
                        "Please delete all appointments attached to this customer before deleting.");
                customerHasAppts.showAndWait();
                break;
            case 5: // overlapping appointment error
                Alert overlappingAppt = createAlert(Alert.AlertType.ERROR, "Error",
                        "This customer already has an appointment scheduled within those times.");
                overlappingAppt.showAndWait();
                break;
            case 6: // outside business hours error
                Alert outsideBusinessHours = createAlert(Alert.AlertType.ERROR, "Error",
                        "Appointment is outside the business hours of 0800-2200 EST.");
                outsideBusinessHours.showAndWait();
                break;
            case 7: // start time after end time error
                Alert startAfterEnd = createAlert(Alert.AlertType.ERROR, "Error",
                        "Appointment start time is after the end time.");
                startAfterEnd.showAndWait();
                break;
            case 8: // invalid login error
                if (french = true) {
                    Alert invalidLogin = createAlert(Alert.AlertType.ERROR, "Error",
                            frBundle.getString("invalidLogin"));
                    invalidLogin.showAndWait();
                    break;
                }
                Alert invalidLogin = createAlert(Alert.AlertType.ERROR, "Error",
                        "Invalid username and/or password.");
                invalidLogin.showAndWait();
                break;
        }
    }

    default boolean isOutsideBusinessHours(Appointment appointment) {
         LocalTime startEST = LocalTime.from(convertLocalToEst(appointment.getStart()));
         LocalTime endEST = LocalTime.from(convertLocalToEst(appointment.getEnd()));

         if (startEST.isBefore(OPENING_TIME) || endEST.isAfter(CLOSING_TIME)) {
             popupError(6);
             return true;
         }
         return false;
    }

    default boolean checkDate(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            popupError(1);
            return false;
        }
        return true;
    }

    default boolean startTimeIsAfterEndTime(Appointment appointment) {
        if (appointment.getStart().isAfter(appointment.getEnd())) {
            popupError(7);
            return true;
        }
        return false;
    }

    default LocalDateTime convertEstToUtc(LocalDateTime localDateTime) {
        ZonedDateTime timeEst = localDateTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime convertedTimeUtc = timeEst.withZoneSameInstant(ZoneId.of("UTC"));
        return convertedTimeUtc.toLocalDateTime();
    }

    default LocalDateTime convertEstToLocal (LocalDateTime localDateTime) {
        ZonedDateTime timeEst = localDateTime.atZone(ZoneId.of("US/Eastern"));
        ZonedDateTime convertedTimeSysDefault = timeEst.withZoneSameInstant(ZoneId.systemDefault());
        return convertedTimeSysDefault.toLocalDateTime();
    }

    default LocalDateTime convertUtcToLocal (LocalDateTime localDateTime) {
        ZonedDateTime timeUtc = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime convertedTimeSysDefault = timeUtc.withZoneSameInstant(ZoneId.systemDefault());
        return convertedTimeSysDefault.toLocalDateTime();
    }

    default LocalDateTime convertUtcToEst (LocalDateTime localDateTime) {
        ZonedDateTime timeUtc = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime convertedTimeEST = timeUtc.withZoneSameInstant(ZoneId.of("EST"));
        return convertedTimeEST.toLocalDateTime();
    }

    default LocalDateTime convertLocalToEst(LocalDateTime localDateTime) {
        ZonedDateTime sysDefaultTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime convertedTimeEST = sysDefaultTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
        return convertedTimeEST.toLocalDateTime();

    }

    default LocalDateTime convertLocalToUtc(LocalDateTime localDateTime) {
        ZonedDateTime sysDefaultTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime convertedTimeUTC = sysDefaultTime.withZoneSameInstant(ZoneId.of("UTC"));
        return convertedTimeUTC.toLocalDateTime();
    }

}
