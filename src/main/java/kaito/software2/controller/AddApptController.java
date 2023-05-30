package kaito.software2.controller;

import javafx.collections.ObservableList;
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
import kaito.software2.utilities.Validate;
import kaito.software2.utilities.Nav;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddApptController extends AppointmentDAO implements Initializable, Validate, Nav {
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
    LocalTime start = LocalTime.of(8, 0 );
    LocalTime end = LocalTime.of(22, 0 );

    private LocalDateTime getLocalDateTime(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM LLLL");
        String formattedDate = localDate.format(formatter);
        System.out.println(formattedDate);
        return null;
    }

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
            start = start.plusMinutes(30);
        }
        try {
            customers.setItems(customerDAO.getAll());
            users.setItems(userDAO.getAll());
            contacts.setItems(contactsDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void save(ActionEvent actionEvent) throws IOException, SQLException {
        String title = this.title.getText();
        String desc = this.desc.getText();
        String location = this.location.getText();
        String type = this.type.getText();
        LocalDate startDate = this.startDate.getValue();
        LocalTime startTime = this.startTime.getValue();
        LocalDate endDate = this.endDate.getValue();
        LocalTime endTime = this.endTime.getValue();
        Customer customer = this.customers.getValue();
        User user = this.users.getValue();
        Contact contact = this.contacts.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate,startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate,endTime);

        // converts start and end times from EST to UTC
        Appointment appt = new Appointment(title, desc, location, type, convertEstToUtc(startDateTime), convertEstToUtc(endDateTime), customer.getId(), user.getUserId(), contact.getId());

        try {
            if (checkDate(this.startDate.getValue(), this.endDate.getValue())) {
                insert(appt);
                switchScene("view/appointment-screen.fxml");
            }
        } catch (NullPointerException e) {
            Validate.popupError(2);
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        switchScene("view/appointment-screen.fxml");
    }
}
