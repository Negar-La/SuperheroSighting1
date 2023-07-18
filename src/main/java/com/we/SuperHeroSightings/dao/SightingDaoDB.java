
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
public class SightingDaoDB implements SightingDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Sighting> getAllSightings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSighting(Sighting sighting) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteSightingByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

    
}
