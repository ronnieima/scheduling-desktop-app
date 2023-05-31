package kaito.software2.model;

import java.time.LocalDateTime;

/**
 * Model class for an Appointment
 */
public class Appointment {
    private int total;
    private String month;
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String countryName;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor for an Appointment that contains all of its attributes
     * @param id Appointment's ID - Will be auto-generated
     * @param title Appointment's title
     * @param description Appointment's description
     * @param location Appointment's location
     * @param type Appointment's type
     * @param start Appointment's start
     * @param end Appointment's end
     * @param customerId Appointment's customerId
     * @param userId Appointment's userId
     * @param contactId Appointment's contactId
     */
    public Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Constructor for an Appointment without the ID - Used for adding to the database so it can generate the ID
     * @param title Appointment's title
     * @param description Appointment's description
     * @param location Appointment's location
     * @param type Appointment's type
     * @param start Appointment's start
     * @param end Appointment's end
     * @param customerId Appointment's customerId
     * @param userId Appointment's userId
     * @param contactId Appointment's contactId
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Constructor for reports to get the total number of customer appointments by type and month
     * @param month Appointment's month
     * @param type Appointment's type
     * @param total Total number of customer appointments by type and month
     */
    public Appointment(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     *  Constructor for reports to get the total number of customer's of each country.
     * @param total Total number of counstomer's for each country
     * @param countryName Name of the countries
     */
    public Appointment(int total, String countryName) {
        this.total = total;
        this.countryName = countryName;
    }

    /**
     * Getter for countryName
     * @return Returns the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for countryName
     * @param countryName New countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    /**
     * Getter for total
     * @return Returns the countryName total
     */
    public int getTotal() {
        return total;
    }
    /**
     * Setter for total
     * @param total New total
     */
    public void setTotal(int total) {
        this.total = total;
    }
    /**
     * Getter for month
     * @return Returns the month
     */
    public String getMonth() {
        return month;
    }
    /**
     * Setter for month
     * @param month New month
     */
    public void setMonth(String month) {
        this.month = month;
    }
    /**
     * Getter for id
     * @return Returns the id
     */
    public int getId() {
        return id;
    }
    /**
     * Setter for id
     * @param id New id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter for title
     * @return Returns the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * Setter for title
     * @param title New title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Getter for description
     * @return Returns the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Setter for description
     * @param description New description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Getter for location
     * @return Returns the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * Setter for location
     * @param location New location
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * Getter for type
     * @return Returns the type
     */
    public String getType() {
        return type;
    }
    /**
     * Setter for type
     * @param type New type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Getter for start
     * @return Returns the start
     */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     * Setter for start
     * @param start New start
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /**
     * Getter for end
     * @return Returns the end
     */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     * Setter for end
     * @param end New end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /**
     * Getter for customerId
     * @return Returns the customerId
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * Setter for customerId
     * @param customerId New customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    /**
     * Getter for userId
     * @return Returns the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * Setter for userId
     * @param userId New userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * Getter for contactId
     * @return Returns the contactId
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * Setter for contactId
     * @param contactId New contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
