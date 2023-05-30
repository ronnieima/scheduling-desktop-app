package kaito.software2.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kaito.software2.model.Country;
import kaito.software2.model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDAO implements DAO<FirstLevelDivision> {

    /**
     * Takes a country ID and returns an array with its divisions.
     * @param countryId Country to filter by
     * @return ObservableList of divisions by selected country
     * @throws SQLException
     */
    public ObservableList<FirstLevelDivision> getDivisionsByCountry(int countryId) throws SQLException {
        ObservableList<FirstLevelDivision> countryDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int newCountryId = rs.getInt("Country_ID");
            FirstLevelDivision newDivision = new FirstLevelDivision(id, division, newCountryId);
            countryDivisions.add(newDivision);
        }
        return countryDivisions;
    }

    @Override
    public FirstLevelDivision get(int id) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        FirstLevelDivision division = null;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int newId = rs.getInt("Division_ID");
            String name = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            division = new FirstLevelDivision(newId, name, countryId);
        }
        return division;
    }

    @Override
    public ObservableList<FirstLevelDivision> getAll() throws SQLException {
        ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int id = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");

            FirstLevelDivision newDivision = new FirstLevelDivision(id, division, countryId);
            allDivisions.add(newDivision);
        }
        return allDivisions;
    }

    @Override
    public int save(FirstLevelDivision firstLevelDivision) throws SQLException {
        return 0;
    }

    @Override
    public int insert(FirstLevelDivision firstLevelDivision) throws SQLException {
        return 0;
    }

    @Override
    public int update(FirstLevelDivision firstLevelDivision) throws SQLException {
        return 0;
    }

    @Override
    public int delete(FirstLevelDivision firstLevelDivision) throws SQLException {
        return 0;
    }
}
