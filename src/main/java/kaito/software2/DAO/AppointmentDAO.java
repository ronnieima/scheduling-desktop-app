package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import kaito.software2.model.Appointment;
import kaito.software2.model.Contact;
import kaito.software2.model.Customer;
import kaito.software2.model.User;
import kaito.software2.utilities.Validate;
import static kaito.software2.controller.LoginScreenController.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 *  DAO class for appointments
 */
public class AppointmentDAO implements DAO<Appointment>, Validate {

    /**
     * Searches through a user's appointments to see if they have an appointment within 15 minutes.
     * @param user User to check.
     * @throws SQLException
     */
    public void checkUpcomingAppointments (User user) throws SQLException {
        boolean appointmentFound = false;
        String sql = "SELECT * FROM appointments WHERE USER_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, user.getUserId());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            LocalDateTime startDateTimeLocal = convertUtcToLocal(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime fifteenMinutesAfterNow = LocalDateTime.now().plusMinutes(15);

            if (startDateTimeLocal.isBefore(fifteenMinutesAfterNow) && startDateTimeLocal.isAfter(LocalDateTime.now())) {
                appointmentFound = true;
                Alert upcomingAppointment = Validate.createAlert(Alert.AlertType.INFORMATION, "Upcoming Appointment", "You have an upcoming appointment within 15 minutes! " + appointmentId + " " + startDateTimeLocal);
                upcomingAppointment.showAndWait();
            }
        }
        // If there is no appointment found, popup an alert that states that there are no upcoming appointments.
        if (!appointmentFound) {
            if (french = true) {
                Alert noAppointments = Validate.createAlert(Alert.AlertType.INFORMATION, "No Upcoming Appointment", "Vous n'avez aucun rendez-vous à venir.");
                noAppointments.showAndWait();
            } else{
                Alert noAppointments = Validate.createAlert(Alert.AlertType.INFORMATION, "No Upcoming Appointment", "You have no upcoming appointments.");
                noAppointments.showAndWait();
            }
        }
    }

    /**
     * Checks if an customer has overlapping appointments
     * @param appointment Appointment to check
     * @param givenStartLocal Appointment Start
     * @param givenEndLocal Appointment End
     * @return Boolean of true or false
     */
    public boolean checkOverlappingAppointments(Appointment appointment, LocalDateTime givenStartLocal, LocalDateTime givenEndLocal) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ps.setInt(1, appointment.getCustomerId());
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            LocalDateTime startLocal = (rs.getTimestamp("Start").toLocalDateTime());
             LocalDateTime endLocal = (rs.getTimestamp("End").toLocalDateTime());
            if (appointmentId != appointment.getId()) {
                // Checks if appointment start is within the window
                if ((givenStartLocal.isAfter(startLocal) || givenStartLocal.equals(startLocal)) && givenStartLocal.isBefore(endLocal)) {
                    return false;
                }
                // Checks if appointment end is within the window
                if (endLocal.isAfter(givenStartLocal) && (endLocal.isBefore(givenEndLocal) || endLocal.isEqual(givenEndLocal))) {
                    return false;
                }
                // Checks if appointment start and end are both outside of the window
                if ((givenStartLocal.isBefore(startLocal) || givenStartLocal.isEqual(startLocal)) && (givenEndLocal.isAfter(endLocal) || givenStartLocal.isEqual(endLocal))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a customer has existing appointments - Used for deletion of customers
     * @param customer Customer to be checked
     * @return List of a customer's appointments
     */
    public ObservableList<Appointment> checkExistingAppointments(Customer customer) throws SQLException {
        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getId());
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");;
            Appointment newAppt = new Appointment(id, title, description, location,type,start,end,customerId,userId,contactId);
            customerAppts.add(newAppt);
        }
        return customerAppts;
    }

    /**
     * Gets a list of the amount of appointments sorted by type and month - Used for reports
     * @return List of # of appointments
     */
    public ObservableList<Appointment> getAppointmentsByTypeAndMonth() throws SQLException {
        ObservableList<Appointment> appointmentsByTypeAndMonth = FXCollections.observableArrayList();
        String sql = "SELECT Type, date_format(start, '%M') as 'Month', COUNT(*) as Total FROM appointments GROUP BY Type, Month ORDER BY Start";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String month = rs.getString("Month");
            String type = rs.getString("Type");
            int total = rs.getInt("Total");
            Appointment newAppointment = new Appointment(month, type, total);
            appointmentsByTypeAndMonth.add(newAppointment);
        }
        return appointmentsByTypeAndMonth;
    }

    /**
     * Gets an individual contact's appointment schedule
     * @param contact Contact to check
     * @return List of appointments for contact
     */
    public ObservableList<Appointment> getContactSchedule(Contact contact) throws SQLException {
        ObservableList<Appointment> contactSchedule = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contact.getId());

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");;
            Appointment appointment = new Appointment(id, title, description, location,type,start,end,customerId,userId,contactId);
            contactSchedule.add(appointment);
        }
        return contactSchedule;
    }

    /**
     * Gets a list of appointments within the current month
     * @return a list of appointments within the current month
     */
    public ObservableList<Appointment> filterByMonth() throws SQLException {
        ObservableList appointmentsByMonth = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        LocalDateTime firstDayLDT = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime lastDayLDT = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth());

        Timestamp firstDayTS = Timestamp.valueOf(firstDayLDT);
        Timestamp lastDayTS = Timestamp.valueOf(lastDayLDT);

        ps.setTimestamp(1, firstDayTS);
        ps.setTimestamp(2, lastDayTS);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");;
            Appointment newAppt = new Appointment(id, title, description, location,type,start,end,customerId,userId,contactId);
            appointmentsByMonth.add(newAppt);
        }

        return appointmentsByMonth;
    }

    /**
     * Gets a list of appointments within the current week
     * @return a list of appointments within the current week
     */
    public ObservableList<Appointment> filterByWeek() throws SQLException {
        ObservableList<Appointment> appointmentsByWeek = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN ? AND ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime sixDaysLater = LocalDateTime.now().plusDays(6);

        Timestamp dateNowTS = Timestamp.valueOf(dateNow);
        Timestamp sixDaysLaterTS = Timestamp.valueOf(sixDaysLater);

        ps.setTimestamp(1, dateNowTS);
        ps.setTimestamp(2, sixDaysLaterTS);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");;
            Appointment newAppt = new Appointment(id, title, description, location,type,start,end,customerId,userId,contactId);
            appointmentsByWeek.add(newAppt);
        }
        return appointmentsByWeek;
    }

    /**
     * Gets an appointment given an appointment ID
     * @param id ID to check
     * @return Appointment object
     */
    @Override
    public Appointment get(int id) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
        Appointment newAppt = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int newId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");;
            newAppt = new Appointment(newId, title, description, location,type,start,end,customerId,userId,contactId);
        }
        return newAppt;
    }

    /**
     * Gets a list of all appointments in the database
     * @return  a list of all appointments in the database
     */
    public ObservableList<Appointment> getAll() throws SQLException {
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");;
            Appointment newAppt = new Appointment(id, title, description, location,type,start,end,customerId,userId,contactId);
            allAppts.add(newAppt);
        }
        return allAppts;
    }

    /**
     * Saves current appointment - Unused
     */
    @Override
    public int save(Appointment appointment) throws SQLException {
        return 0;
    }


    /**
     * Inserts an appointment into the database
     * @param appt Appointment to insert
     * @return Number of rows affected
     */
    public int insert(Appointment appt) throws SQLException {
        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appt.getId());
        ps.setString(2, appt.getTitle());
        ps.setString(3, appt.getDescription());
        ps.setString(4, appt.getLocation());
        ps.setString(5, appt.getType());
        ps.setTimestamp(6, Timestamp.valueOf(appt.getStart()));
        ps.setTimestamp(7, Timestamp.valueOf(appt.getEnd()));
        ps.setInt(8, appt.getCustomerId());
        ps.setInt(9, appt.getUserId());
        ps.setInt(10, appt.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an appointment's attributes
     * @param appt Appointment to update
     * @return Number of rows affected
     */
    @Override
    public int update(Appointment appt) throws SQLException {

        String sql = "UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appt.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appt.getEnd()));
        ps.setInt(7, appt.getCustomerId());
        ps.setInt(8, appt.getUserId());
        ps.setInt(9, appt.getContactId());
        ps.setInt(10, appt.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes an appointment from the database
     * @param appt Appointment to delete
     * @return  Number of rows affected
     */
    @Override
    public int delete(Appointment appt) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appt.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


}
