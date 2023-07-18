
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Power;
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
public class PowerDaoDB implements PowerDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Power> getAllPowers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Power addPower(Power power) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updatePower(Power power) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deletePowerByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

}
