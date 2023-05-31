package kaito.software2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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


public class ModApptController extends AppointmentDAO implements Initializable, Nav {
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
    public static Appointment appt = null;
    LocalTime start = LocalTime.of(0, 0 );
    LocalTime end = LocalTime.of(23, 0 );

    /**
     * Initialize method that sets the title and populates the fields with an Appointment's info to modify
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.getStage().setTitle("Appointment Scheduler - Modify Appointment");

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

        id.setText(String.valueOf(appt.getId()));
        title.setText(appt.getTitle());
        desc.setText(appt.getDescription());
        location.setText(appt.getLocation());
        type.setText(appt.getType());
        startDate.setValue(appt.getStart().toLocalDate());
        startTime.setValue(LocalTime.from(convertLocalToEst(appt.getStart())));
        endDate.setValue(appt.getEnd().toLocalDate());
        endTime.setValue(LocalTime.from(convertLocalToEst(appt.getEnd())));
        try {
            customers.setValue(customerDAO.get(appt.getCustomerId()));
            users.setValue(userDAO.get(appt.getUserId()));
            contacts.setValue(contactsDAO.get(appt.getContactId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Allows the "receiving" of an Appointment object from another class.
     * @param appointment Appointment being passed
     */
    public static void passAppointment(Appointment appointment) {
        appt = appointment;
    }

    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        int id = Integer.parseInt(this.id.getText());
        String title = this.title.getText();
        String desc = this.desc.getText();
        String location = this.location.getText();
        String type = this.type.getText();
        LocalDateTime start = convertEstToLocal(LocalDateTime.of(startDate.getValue(), startTime.getValue()));
        LocalDateTime end = convertEstToLocal(LocalDateTime.of(endDate.getValue(), endTime.getValue()));
        int custId = customers.getValue().getId();
        int userId = users.getValue().getUserId();
        int contactId = contacts.getValue().getId();
        Appointment newAppointment = new Appointment(id,title, desc, location, type, start, end, custId, userId, contactId);

        try {
            if(!isOutsideBusinessHours(newAppointment) && !startTimeIsAfterEndTime(newAppointment)) {
                if (checkDate(this.startDate.getValue(), this.endDate.getValue()) && checkOverlappingAppointments(newAppointment, start, end)) {
                    update(newAppointment);
                    switchScene("view/appointment-screen.fxml");
                } else {
                    Validate.popupError(5);
                }
            }
        } catch (NullPointerException e) {
            Validate.popupError(2);
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        switchScene("view/appointment-screen.fxml");
    }
}
