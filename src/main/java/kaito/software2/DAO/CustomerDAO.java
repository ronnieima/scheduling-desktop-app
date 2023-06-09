package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.Appointment;
import kaito.software2.model.Customer;
import kaito.software2.utilities.Validate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DAO class for customers
 */
public class CustomerDAO implements DAO<Customer>, Validate {

    /**
     * Used to get reports for how many customers are in each country.
     * @return List of how many customers are in each country
     */
    public ObservableList<Appointment> getCustomersByCountry() throws SQLException {
        ObservableList<Appointment> customersByCountry = FXCollections.observableArrayList();
        String sql = "SELECT Country , COUNT(Country) as 'Total Customers' " +
                "FROM first_level_divisions " +
                "JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID " +
                "JOIN customers ON first_level_divisions.Division_ID=customers.Division_ID " +
                "GROUP BY Country";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String countryName = rs.getString("Country");
            int total = rs.getInt("Total Customers");
            Appointment newAppointment = new Appointment(total, countryName);
            customersByCountry.add(newAppointment);
        }
        return customersByCountry;
    }

    /**
     * Gets an customer given an customer ID
     * @param id ID to check
     * @return Customer object
     */
    @Override
    public Customer get(int id) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        Customer newCust = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int newId = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            newCust = new Customer(newId, name, address, postalCode, phone, divisionId);
        }
        return newCust;
    }

    /**
     * Gets a list of all customers in the database
     * @return  a list of all customers in the database
     */
    public ObservableList<Customer> getAll() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            Customer newCust = new Customer(id, name, address, postalCode, phone, divisionId);
            allCustomers.add(newCust);
        }
        return allCustomers;
    }

    /**
     * Saves current contact - Unused
     */
    @Override
    public int save(Customer customer) throws SQLException {
        return 0;
    }

    /**
     * Inserts a customer into the database
     * @param customer Customer to insert
     * @return Number of rows affected
     */
    @Override
    public int insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getId());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getPostalCode());
        ps.setString(5, customer.getPhone());
        ps.setInt(6, customer.getDivisionId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates an customer's attributes
     * @param customer Customer to update
     * @return Number of rows affected
     */
    @Override
    public int update(Customer customer) throws SQLException {
        String sql = "UPDATE customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        ps.setInt(6, customer.getId());

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes an Customer from the database
     * @param customer Customer to delete
     * @return  Number of rows affected
     */
    @Override
    public int delete(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
