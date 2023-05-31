package kaito.software2.model;

/**
 * Model class for customer
 */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    /**
     * Constructor for a customer
     * @param id Customer's id
     * @param name Customer's name
     * @param address Customer's address
     * @param postalCode Customer's postalCode
     * @param phone Customer's phone
     * @param divisionId Customer's divisionId
     */
    public Customer(int id, String name, String address, String postalCode, String phone, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Constructor for a customer without the ID - used to add to database
     * @param name  Customer name
     * @param address Customer address
     * @param postalCode Customer postalCode
     * @param phone Customer phone
     * @param divisionId Customer divisionId
     */
    public Customer(String name, String address, String postalCode, String phone, int divisionId) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * Overridden method which displays its attributes as a String
     * @return Customer's attributes as a String
     */
    @Override
    public String toString() {
        return name + " | ID: " + id + " | Phone: " + phone;
    }

    /**
     * Getter for ID
     * @return Customer ID
     */
    public int getId() {
        return id;
    }
       /**
     * Getter for name
     * @return Customer name
     */
    public String getName() {
        return name;
    }
       /**
     * Getter for address
     * @return Customer address
     */
    public String getAddress() {
        return address;
    }
       /**
     * Getter for postalCode
     * @return Customer postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }
       /**
     * Getter for phone
     * @return Customer phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Getter for divisionId
     * @return Customer divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

}
