package kaito.software2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {

    private int id;
    private String name;
    private String email;
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " | ID: " + id + " | Email: " + email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }
    public static void setAllContacts(ObservableList<Contact> allContacts) {
        Contact.allContacts = allContacts;
    }
}
