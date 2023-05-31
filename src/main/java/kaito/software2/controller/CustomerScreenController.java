package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.DAO.CustomerDAO;
import kaito.software2.model.Customer;
import kaito.software2.Main;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static kaito.software2.utilities.Validate.createAlert;
import static kaito.software2.utilities.Validate.popupError;

public class CustomerScreenController extends CustomerDAO implements Initializable, Nav {
    public Button viewApptsButton;
    public Button logoutButton;
    public ToggleGroup apptFilter;
    public RadioButton viewByMonthRadio;
    public RadioButton viewByWeekRadio;
    public RadioButton viewAllRadio;
    public TableView<Customer> custTable;
    public TableColumn id;
    public TableColumn name;
    public TableColumn address;
    public TableColumn postalCode;
    public TableColumn phone;
    public TableColumn division;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Customer List");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        division.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        try {
            custTable.setItems(getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void logout(ActionEvent actionEvent) throws IOException {
        logout();
    }

    public void viewAppts(ActionEvent actionEvent) throws IOException {
        switchScene("view/appointment-screen.fxml");
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        switchScene("view/add-customer.fxml");
    }

    public void modifyCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            ModCustController.passCustomer(selectedCustomer);
            switchScene("view/modify-customer.fxml");
        } else {
            popupError(3);
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            // Checks if selected customer has appointments before deleting
            if (appointmentDAO.checkExistingAppointments(selectedCustomer).isEmpty()) {
                Alert delConf = createAlert(Alert.AlertType.CONFIRMATION, "Delete", "Are you sure you would like to delete this customer?");
                if(delConf.showAndWait().get() == ButtonType.OK){
                    delete(selectedCustomer);
                    custTable.setItems(getAll());
                    Alert deleted = createAlert(Alert.AlertType.INFORMATION, "Customer deleted", selectedCustomer.getName() + " has been deleted.");
                    deleted.showAndWait();
                }
            } else {
                popupError(4);  // Customer has appointments error
            }
        } else {
            popupError(3);  // No customer selected error
        }
    }

    public void viewByMonth(ActionEvent actionEvent) {
    }

    public void viewByWeek(ActionEvent actionEvent) {
    }

    public void viewAll(ActionEvent actionEvent) {
    }

    public void pullReports(ActionEvent actionEvent) throws IOException {
        switchScene("view/reports.fxml");
    }
}
