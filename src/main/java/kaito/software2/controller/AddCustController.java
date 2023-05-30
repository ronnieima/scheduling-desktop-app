package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import kaito.software2.DAO.CountryDAO;
import kaito.software2.DAO.CustomerDAO;
import kaito.software2.DAO.DivisionDAO;
import kaito.software2.Main;
import kaito.software2.model.*;
import kaito.software2.utilities.Nav;
import kaito.software2.utilities.Validate;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddCustController extends CustomerDAO implements Initializable, Nav  {
    public TextField id;
    public TextField name;
    public TextField address;
    public TextField postalCode;
    public TextField phone;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox<FirstLevelDivision> division;
    public ComboBox<Country> country;
    CountryDAO countryDAO = new CountryDAO();
    DivisionDAO divisionDAO = new DivisionDAO();

    //TODO finish this hoe with validation
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Add Customer");

        try {
            country.setItems(countryDAO.getAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        String name = this.name.getText();
        String address = this.address.getText();
        String postalCode = this.postalCode.getText();
        String phone = this.phone.getText();
        int divisionId = division.getValue().getId();

        Customer newCustomer = new Customer(name, address, postalCode,phone,divisionId);

        try {
            insert(newCustomer);
            switchScene("view/customer-screen.fxml");
        } catch (NullPointerException npe) {
            Validate.popupError(2);
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        switchScene("view/customer-screen.fxml");
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
}
