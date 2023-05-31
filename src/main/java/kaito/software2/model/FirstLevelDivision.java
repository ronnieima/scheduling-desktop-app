package kaito.software2.model;

/**
 * Model class for a first level division
 */
public class FirstLevelDivision {

    private int id;
    private String division;
    private int countryId;

    /**
     * Constructor for a first level division
     * @param id Division ID
     * @param division Division Name
     * @param countryId Country ID
     */
    public FirstLevelDivision(int id, String division, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }
    /**
     * Overridden method which displays its attributes as a String
     * @return Division's attributes as a String
     */
    @Override
    public String toString() {
        return division;
    }

    /**
     * Getter for ID
     * @return Division ID
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for countryId
     * @return Country's ID
     */
    public int getCountryId() {
        return countryId;
    }

}
