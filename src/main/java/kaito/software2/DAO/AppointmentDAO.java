package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.Appointment;
import kaito.software2.model.Customer;
import kaito.software2.model.User;
import kaito.software2.utilities.Validate;

import java.sql.*;
import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class AppointmentDAO implements DAO<Appointment>, Validate {

    public boolean hasUpcomingAppointments (User user) {
        return true;
    }

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

    @Override
    public int save(Appointment appointment) throws SQLException {
        return 0;
    }


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
    @Override
    public int delete(Appointment appt) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appt.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


}
