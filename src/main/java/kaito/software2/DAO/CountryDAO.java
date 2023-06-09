package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.Country;
import kaito.software2.model.Customer;
import kaito.software2.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO class for countries
 */
public class CountryDAO implements DAO<Country> {
    @Override
    public Country get(int id) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        Country country = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int newId = rs.getInt("Country_ID");
            String name = rs.getString("Country");
            country = new Country(newId, name);
        }
        return country;
    }

    /**
     * Gets a list of all countries in the database
     * @return  a list of all countries in the database
     */
    @Override
    public ObservableList<Country> getAll() throws SQLException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Country_ID");
            String country = rs.getString("Country");

            Country newCountry = new Country(id, country);
            allCountries.add(newCountry);
        }
        return allCountries;
    }

    /**
     * Saves current country - Unused
     */
    @Override
    public int save(Country country) throws SQLException {
        return 0;
    }

    /**
     * Inserts current country - Unused
     */
    @Override
    public int insert(Country country) throws SQLException {
        return 0;
    }

    /**
     * Updates current country - Unused
     */
    @Override
    public int update(Country country) throws SQLException {
        return 0;
    }

    /**
     * Deletes current country - Unused
     */
    @Override
    public int delete(Country country) throws SQLException {
        return 0;
    }
}
