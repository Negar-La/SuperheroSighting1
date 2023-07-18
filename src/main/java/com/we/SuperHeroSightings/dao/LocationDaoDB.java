
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Location> getAllLocations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Location addLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteLocationByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Location> getLocationsByHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
