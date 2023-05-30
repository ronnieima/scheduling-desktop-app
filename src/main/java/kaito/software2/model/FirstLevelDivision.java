package kaito.software2.model;

public class FirstLevelDivision {

    private int id;
    private String division;
    private int countryId;

    public FirstLevelDivision(int id, String division, int countryId) {
        this.id = id;
        this.division = division;
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return division;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}
