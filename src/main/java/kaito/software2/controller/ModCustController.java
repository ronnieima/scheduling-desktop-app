package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import kaito.software2.DAO.CountryDAO;
import kaito.software2.DAO.CustomerDAO;
import kaito.software2.DAO.DivisionDAO;
import kaito.software2.Main;
import kaito.software2.model.Country;
import kaito.software2.model.Customer;
import kaito.software2.model.FirstLevelDivision;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller class for the modify customer screen
 */
public class ModCustController extends CustomerDAO implements Initializable, Nav {
    public TextField id;
    public TextField name;
    public TextField address;
    public TextField postalCode;
    public TextField phone;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox<FirstLevelDivision> division;
    public ComboBox<Country> country;
    private static Customer customer = null;
    DivisionDAO divisionDAO = new DivisionDAO();
    CountryDAO countryDAO = new CountryDAO();

    /**
     * Initialize class which sets and populates the selected customer's attributes for all fields except ID
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Modify Customer");

        id.setText(String.valueOf(customer.getId()));
        name.setText(customer.getName());
        address.setText(customer.getAddress());
        postalCode.setText(customer.getPostalCode());
        phone.setText(customer.getPhone());
        try {
            // Sets the values for the combo boxes
            country.setValue(countryDAO.get(divisionDAO.get(customer.getDivisionId()).getCountryId()));
            division.setValue(divisionDAO.get(customer.getDivisionId()));
            // Populates the options for the combo boxes
            country.setItems(countryDAO.getAll());
            if (country.getValue().getId() == 1) {
                division.setItems(divisionDAO.getDivisionsByCountry(1));
            } else if (country.getValue().getId() == 2) {
                division.setItems(divisionDAO.getDivisionsByCountry(2));
            } else if (country.getValue().getId() == 3) {
                division.setItems(divisionDAO.getDivisionsByCountry(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines what items in the division combo box to be filled depending on the selected country
     * @throws SQLException Throws an SQL Exception
     */
    public void countryAction() throws SQLException {
        division.setValue(null);
        if (country.getValue().getId() == 1) {
            division.setItems(divisionDAO.getDivisionsByCountry(1));
        } else if (country.getValue().getId() == 2) {
            division.setItems(divisionDAO.getDivisionsByCountry(2));
        } else if (country.getValue().getId() == 3) {
            division.setItems(divisionDAO.getDivisionsByCountry(3));
        }
    }

    /**
     * Allows the "receiving" of an Appointment object from another class.
     * @param selectedCustomer Appointment being passed
     */
    public static void passCustomer(Customer selectedCustomer) {
        customer = selectedCustomer;
    }

    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        int id = Integer.parseInt(this.id.getText());
        String name = this.name.getText();
        String address = this.address.getText();
        String postalCode = this.postalCode.getText();
        String phone = this.phone.getText();
        int divisionId = division.getValue().getId();
        Customer newCustomer = new Customer(id, name, address, postalCode, phone, divisionId);
        update(newCustomer);
        switchScene("view/customer-screen.fxml");
    }

    /**
     * Navigates back to the customer screen
     */
    public void cancel() throws IOException {
        switchScene("view/customer-screen.fxml");
    }


}
