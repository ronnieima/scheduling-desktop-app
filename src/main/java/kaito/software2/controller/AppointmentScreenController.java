package kaito.software2.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.Main;
import kaito.software2.model.Appointment;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static kaito.software2.utilities.Validate.createAlert;
import static kaito.software2.utilities.Validate.popupError;

/**
 * Controller class for the appointment screen
 */
public class AppointmentScreenController extends AppointmentDAO implements Initializable, Nav {

    public Button logoutButton;
    public Button viewCustomersButton;
    public RadioButton viewByMonthRadio;
    public ToggleGroup apptFilter;
    public RadioButton viewByWeekRadio;
    public RadioButton viewAllRadio;
    public TableView<Appointment> apptTable;
    public TableColumn id;
    public TableColumn title;
    public TableColumn desc;
    public TableColumn location;
    public TableColumn contact;
    public TableColumn type;
    public TableColumn startDateTime;
    public TableColumn endDateTime;
    public TableColumn custId;
    public TableColumn userId;

    /**
     * Initialize method which sets up the table view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - My Appointments");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        custId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        try {
            apptTable.setItems(getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Logs out of the program
     */
    public void logoutOnAction() throws IOException {
        logout();
    }

    /**
     * Navigates to customer screen
     */
    public void viewCustomers() throws IOException {
        switchScene("view/customer-screen.fxml");
    }

    /**
     * Navigates to add appointment screen
     */
    public void addAppointment() throws IOException {
        switchScene("view/add-appointment.fxml");
    }

    /**
     * Navigates to modify appointment screen
     */
    public void modifyAppointment() {
        try {
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();

            ModApptController.passAppointment(selectedAppt);
            switchScene("view/modify-appointment.fxml");
        } catch (Exception npe) {
            popupError(3);
        }
    }

    /**
     * Deletes a selected appointment from the database
     */
    public void deleteAppointment() throws SQLException {
        Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();

        if (selectedAppt != null) {
            Alert delConf = createAlert(Alert.AlertType.CONFIRMATION, "Delete", "Are you sure you would like to delete this appointment?");
            if (delConf.showAndWait().get() == ButtonType.OK) {
                delete(selectedAppt);
                apptTable.setItems(getAll());
                Alert deleted = createAlert(Alert.AlertType.INFORMATION, "Appointment deleted", "Appointment ID " + selectedAppt.getId() + " of type " + selectedAppt.getType() + " has been deleted.");
                deleted.showAndWait();
            }
        } else {
            popupError(3);
        }
    }

    /**
     * Radio button which filters the appointments by the current month
     */
    public void viewByMonth() throws SQLException {
        apptTable.setItems(filterByMonth());
    }
    /**
     * Radio button which filters the appointments by the week
     */
    public void viewByWeek() throws SQLException {
        apptTable.setItems(filterByWeek());
    }

    /**
     * Radio button which shows all of the appointments
     */
    public void viewAll() throws SQLException {
        apptTable.setItems(getAll());
    }

    /**
     * Navigates to the reports screen
     */
    public void pullReports() throws IOException {
        switchScene("view/reports.fxml");
    }
}
