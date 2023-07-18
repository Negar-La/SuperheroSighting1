package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jtriolo
 */
@Repository
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationByID(int id) {
        final String sql = "SELECT * FROM location WHERE LocationPK = ?;";
        Location location = jdbc.queryForObject(sql, new LocationMapper(), id);
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        final String sql = "SELECT * FROM location";
        return jdbc.query(sql, new LocationMapper());
    }

    @Override
    public Location addLocation(Location location) {
        final String sql = "INSERT INTO Location(LocationName, Description, LocationAddress, Latitude, Longitude) VALUES(?,?, ?, ?, ?);";
        jdbc.update(sql,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        final String sql = "UPDATE location SET "
                + "LocationName = ?,"
                + "Description = ?,"
                + "LocationAddress = ?,"
                + "Latitude = ?,"
                + "Longitude = ?"
                + "WHERE LocationPK = ?";
        jdbc.update(sql,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    public void deleteLocationByID(int id) {
        final String DELETE_SIGHTING = "DELETE FROM sightings WHERE LocationPK = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_LOCATION = "DELETE FROM location WHERE LocationPK = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        final String SELECT_LOCATIONS_BY_HERO = "SELECT * FROM location l" +
                "JOIN sightings s ON l.LocationPK = s.LocationPK WHERE s.HeroPK = ?";
        return jdbc.query(SELECT_LOCATIONS_BY_HERO, new LocationMapper(), hero.getId());
    }

    class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location location = new Location();

            location.setName(rs.getString("LocationName"));
            location.setDescription(rs.getString("Description"));
            location.setAddress(rs.getString("LocationAddress"));
            location.setLatitude(rs.getString("Latitude"));
            location.setLongitude(rs.getString("Longitude"));
            return location;
        }
    }
}