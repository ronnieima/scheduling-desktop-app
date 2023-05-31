package kaito.software2.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.DAO.ContactsDAO;
import kaito.software2.DAO.CustomerDAO;
import kaito.software2.Main;
import kaito.software2.model.Appointment;
import kaito.software2.model.Contact;
import kaito.software2.utilities.Nav;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportsController extends AppointmentDAO implements Initializable, Nav {
    public Button viewApptsButton;

    public TableView reportTable1;
    public ComboBox<Contact> contactSelect;
    public TableColumn id;
    public TableColumn title;
    public TableColumn type;
    public TableColumn desc;
    public TableColumn startDateTime;
    public TableColumn endDateTime;
    public TableColumn custId;

    public TableView<Appointment> reportTable2;
    public TableColumn<Appointment, Month> table2MonthCol;
    public TableColumn<Appointment, String> table2TypeCol;
    public TableColumn<Appointment, Integer> table2AmtCol;

    public TableView reportTable3;
    public TableColumn table3CountryCol;
    public TableColumn table3AmtCol;

    /**
     * Sets up the TableViews and populates the contact ComboBox upon initialization
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Reports");

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateTime.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTime.setCellValueFactory(new PropertyValueFactory<>("end"));
        custId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        table2MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        table2TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        table2AmtCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        table3CountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        table3AmtCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        ContactsDAO contactsDAO = new ContactsDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        try {
            contactSelect.setItems(contactsDAO.getAll());
            reportTable2.setItems(getAppointmentsByTypeAndMonth());
            reportTable3.setItems(customerDAO.getCustomersByCountry());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Populates the table with the selected contact from the ComboBox
     * @throws SQLException Throws an SQLException
     */
    public void contactSelectOnAction() throws SQLException {
        Contact selectedContact = contactSelect.getValue();

        reportTable1.setItems(getContactSchedule(selectedContact));
    }

    /**
     * Returns back to the appointment screen
     * @throws IOException Throws an IOException
     */
    public void viewAppts() throws IOException {
        switchScene("view/appointment-screen.fxml");
    }



}
