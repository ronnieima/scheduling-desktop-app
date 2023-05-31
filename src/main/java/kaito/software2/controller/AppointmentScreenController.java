package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.Main;
import kaito.software2.model.Appointment;
import kaito.software2.utilities.Nav;
import kaito.software2.utilities.Validate;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static kaito.software2.utilities.Validate.createAlert;
import static kaito.software2.utilities.Validate.popupError;


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

    public void logout(ActionEvent actionEvent) throws IOException {
        logout();
    }

    public void viewCustomers(ActionEvent actionEvent) throws IOException {
        switchScene("view/customer-screen.fxml");
    }

    public void addAppointment(ActionEvent actionEvent) throws IOException {
        switchScene("view/add-appointment.fxml");
    }

    public void modifyAppointment(ActionEvent actionEvent) throws IOException {
        try {
            Appointment selectedAppt = apptTable.getSelectionModel().getSelectedItem();

            ModApptController.passAppointment(selectedAppt);
            switchScene("view/modify-appointment.fxml");
        } catch (Exception npe) {
            popupError(3);
        }
    }

    public void deleteAppointment(ActionEvent actionEvent) throws IOException, SQLException {
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

    public void viewByMonth(ActionEvent actionEvent) throws SQLException {
        apptTable.setItems(filterByMonth());
    }

    public void viewByWeek(ActionEvent actionEvent) throws SQLException {
        apptTable.setItems(filterByWeek());
    }

    public void viewAll(ActionEvent actionEvent) throws SQLException {
        apptTable.setItems(getAll());
    }

    public void pullReports() throws IOException {
        switchScene("view/reports.fxml");
    }
}
