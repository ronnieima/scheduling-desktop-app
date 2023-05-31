package kaito.software2.model;

/**
 * Model class for a contact
 */
public class Contact {

    private int id;
    private String name;
    private String email;

    /**
     * Constructor for a contact
     * @param id Contact's ID
     * @param name Contact name
     * @param email Contact email
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Overridden method which displays its attributes as a String
     * @return Contact's attributes as a String
     */
    @Override
    public String toString() {
        return name + " | ID: " + id + " | Email: " + email;

    }

    /**
     * Getter for ID
     * @return Contact ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id Contact ID
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter for name
     * @return Contact name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter for name
     * @param name Contact name
     */
    public void setName(String name) {
        this.name = name;
    }

}
