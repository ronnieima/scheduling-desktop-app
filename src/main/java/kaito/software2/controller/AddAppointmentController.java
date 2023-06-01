package kaito.software2.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kaito.software2.DAO.AppointmentDAO;
import kaito.software2.DAO.ContactsDAO;
import kaito.software2.DAO.CustomerDAO;
import kaito.software2.DAO.UserDAO;
import kaito.software2.Main;
import kaito.software2.model.Appointment;
import kaito.software2.model.Contact;
import kaito.software2.model.Customer;
import kaito.software2.model.User;
import kaito.software2.utilities.Nav;
import kaito.software2.utilities.Validate;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Controller class for the add appointment screen
 */
public class AddAppointmentController extends AppointmentDAO implements Initializable, Validate, Nav {
    public TextField id;
    public TextField title;
    public TextField desc;
    public TextField location;
    public TextField type;
    public DatePicker startDate;
    public ComboBox<LocalTime> startTime;
    public DatePicker endDate;
    public ComboBox<LocalTime> endTime;
    public Button saveButton;
    public Button cancelButton;
    public ComboBox<Customer> customers;
    public ComboBox<User> users;
    public ComboBox<Contact> contacts;
    LocalTime start = LocalTime.of(0, 0 );
    LocalTime end = LocalTime.of(23, 0 );

    /**
     * Initializer method which populates start and end time for the combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Add Appointment");
        CustomerDAO customerDAO = new CustomerDAO();
        UserDAO userDAO = new UserDAO();
        ContactsDAO contactsDAO = new ContactsDAO();
        // Populates start and end time combo boxes with times
        while (start.isBefore(end.plusSeconds(1))) {
            startTime.getItems().add(start);
            endTime.getItems().add(start);
            start = start.plusMinutes(15);
        }
        try {
            customers.setItems(customerDAO.getAll());
            users.setItems(userDAO.getAll());
            contacts.setItems(contactsDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Saves all of the inputs and adds the new appointment to the database
     * @throws IOException Throws IOException
     * @throws SQLException Throws SQLException
     */
    public void save() throws IOException, SQLException {
        try {
            String title = this.title.getText();
            String desc = this.desc.getText();
            String location = this.location.getText();
            String type = this.type.getText();
            Customer customer = this.customers.getValue();
            User user = this.users.getValue();
            Contact contact = this.contacts.getValue();
            LocalDateTime start = convertEstToLocal(LocalDateTime.of(startDate.getValue(), startTime.getValue()));
            LocalDateTime end = convertEstToLocal(LocalDateTime.of(endDate.getValue(), endTime.getValue()));

            Appointment newAppointment = new Appointment(title, desc, location, type, start, end, customer.getId(), user.getUserId(), contact.getId());

            if(!isOutsideBusinessHours(newAppointment) && !startTimeIsAfterEndTime(newAppointment)) {
                if (checkDate(this.startDate.getValue(), this.endDate.getValue())) {
                    if (checkOverlappingAppointments(newAppointment, start, end)) {
                        insert(newAppointment);
                        switchScene("view/appointment-screen.fxml");
                    } else {
                        Validate.popupError(5);
                    }
                }
            }
        } catch (Exception e) {
            Validate.popupError(2);
        }
    }

    /**
     * Returns back to the appointment screen
     * @throws IOException Throws IOException
     */
    public void cancel() throws IOException {
        switchScene("view/appointment-screen.fxml");
    }
}
