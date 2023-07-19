
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        final String sql = "SELECT SightingDate, Description, HeroPK, LocationPK FROM sighting WHERE SightingPK = ?;";
        return jdbc.queryForObject(sql, new SightingMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String sql = "SELECT * FROM sighting;";
        return jdbc.query(sql, new SightingMapper());
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String sql = "INSERT INTO sighting(SightingDate, Description, HeroPK, LocationPK) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, sighting.getDate());
            statement.setString(2, sighting.getDescription());
            statement.setInt(3, sighting.getHero().getId());
            statement.setInt(4, sighting.getLocation().getId());
            return statement;
        }, keyHolder);

        sighting.setId(keyHolder.getKey().intValue());
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String sql = "UPDATE sighting SET SightingDate = ?, Description = ?, HeroPK = ?, LocationPK = ? WHERE SightingPK = ?;";
        jdbc.update(sql,
                sighting.getDate(),
                sighting.getDescription(),
                sighting.getHero().getId(),
                sighting.getLocation().getId(),
                sighting.getId()
                );
    }

    @Override
    public void deleteSightingByID(int id) {
        final String sql = "DELETE FROM sighting WHERE SightingPK = ?;";
        jdbc.update(sql, id);
    }

    @Override
    public List<Sighting> getSightingsByDate(Date date) {
        final String sql = "SELECT SightingDate, Description, HeroPK, LocationPK FROM sightings WHERE date = ?;";
        return jdbc.query(sql, new SightingMapper(), date);
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        final String sql = "SELECT SightingDate, Description, HeroPK, LocationPK FROM sightings WHERE LocationPK = ?;";
        return jdbc.query(sql, new SightingMapper(), location.getId());
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        final String sql = "SELECT SightingDate, Description, HeroPK, LocationPK FROM sightings WHERE HeroPK = ?;";
        return jdbc.query(sql, new SightingMapper(), hero.getId());
    }

    public static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            HeroDao superDao = new HeroDaoDB();
            LocationDao locationDao = new LocationDaoDB();

            Sighting sighting = new Sighting();

            sighting.setId(rs.getInt("SightingPK"));
            sighting.setDate(rs.getTimestamp("SightingDate").toLocalDateTime());
            sighting.setDescription(rs.getString("Description"));
            sighting.setHero(superDao.getHeroByID(rs.getInt("HeroPK")));
            sighting.setLocation(locationDao.getLocationByID(rs.getInt("LocationPK")));

            return sighting;
        }
    }

    
}
