package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.Appointment;
import kaito.software2.model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDAO implements DAO<Contact>{

    @Override
    public Contact get(int contactId) throws SQLException {
        Contact contact = null;
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            contact = new Contact(id, name, email);
        }
        return contact;
    }

    public ObservableList<Contact> getAll() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contact newContact = new Contact(id, name, email);
            allContacts.add(newContact);
        }
        return allContacts;
    }

    @Override
    public int save(Contact contact) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Contact contact) throws SQLException {
        return 0;
    }

    @Override
    public int update(Contact contact) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Contact contact) throws SQLException {
        return 0;
    }


}
