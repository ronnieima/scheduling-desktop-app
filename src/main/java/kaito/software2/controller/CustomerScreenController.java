package kaito.software2.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.DAO.CustomerDAO;
import kaito.software2.Main;
import kaito.software2.model.Customer;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static kaito.software2.utilities.Validate.createAlert;
import static kaito.software2.utilities.Validate.popupError;

/**
 * Controller class for the customer screen
 */
public class CustomerScreenController extends CustomerDAO implements Initializable, Nav {
    public Button viewApptsButton;
    public Button logoutButton;
    public TableView<Customer> custTable;
    public TableColumn id;
    public TableColumn name;
    public TableColumn address;
    public TableColumn postalCode;
    public TableColumn phone;
    public TableColumn division;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    /**
     * Initialize method which sets up the table view
     */
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

    /**
     * Logs the user out of the program
     */
    public void logoutOnAction() throws IOException {
        logout();
    }

    /**
     * Navigates back to the appointments screen
     */
    public void viewAppts() throws IOException {
        switchScene("view/appointment-screen.fxml");
    }

    /**
     * Navigates to the add customer screen
     */
    public void addCustomer() throws IOException {
        switchScene("view/add-customer.fxml");
    }

    /**
     * Navigates to the modify customer screen
     */
    public void modifyCustomer() throws IOException {
        Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            ModCustController.passCustomer(selectedCustomer);
            switchScene("view/modify-customer.fxml");
        } else {
            popupError(3);
        }
    }

    /**
     * Deletes a selected customer from the database
     */
    public void deleteCustomer() throws SQLException {
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

    /**
     * Navigates to the reports screen
     */
    public void pullReports() throws IOException {
        switchScene("view/reports.fxml");
    }
}
