package kaito.software2.model;

/**
 * Model class for a country
 */
public class Country {
    private int id;
    private String name;

    /**
     * Constructor for country
     * @param id Country ID
     * @param name Country name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Overridden method which displays its attributes as a String
     * @return Country's attributes as a String
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Getter for ID
     * @return Country's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for ID
     * @param id Country's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for name
     * @return Country's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name Country name
     */
    public void setName(String name) {
        this.name = name;
    }
}
