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
        try {

            final String GET_POWER_BY_ID = "SELECT * FROM Power WHERE PowerPK = ?";
            return jdbc.queryForObject(GET_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        String sql = "SELECT * FROM Power;";
        return jdbc.query(sql, new PowerMapper());
    }

    @Override
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO Power (Power, Description) VALUES (?,?)";

        jdbc.update(INSERT_POWER,
                power.getName(),
                power.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE Power SET Power = ?, Description =? WHERE PowerPK = ?";
        jdbc.update(UPDATE_POWER, power.getName(), power.getDescription(), power.getId());
    }

    @Override
    public void deletePowerByID(int id) {

        final String DELETE_POWER = "DELETE FROM Power WHERE PowerPk = ?";
        jdbc.update("Update Hero set PowerPK = NULL where PowerPK = ?", id);
        jdbc.update(DELETE_POWER, id);
    }

    public class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int rowNum) throws SQLException {
            Power power = new Power();
            power.setId(rs.getInt("PowerPK"));
            power.setName(rs.getString("Power"));
            power.setDescription(rs.getString("Description"));

            return power;
        }

    }

}
