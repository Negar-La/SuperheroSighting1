package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Power;
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
        try {
            final String sql = "SELECT SightingDate, Description, HeroPK, LocationPK FROM sighting WHERE SightingPK = ?;";
            Sighting sighting = jdbc.queryForObject(sql, new SightingMapper(), id);
            insertHero(sighting);
            insertLocation(sighting);
            return sighting;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String sql = "SELECT * FROM sighting;";
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper());
        for (Sighting sighting: sightings) {
            insertLocation(sighting);
            insertHero(sighting);
        }
        return sightings;
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String sql = "INSERT INTO sighting(SightingDate, Description, HeroPK, LocationPK) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setObject(1, sighting.getDate());
            statement.setString(2, sighting.getDescription());
            statement.setInt(3, sighting.getHero().getId());
            statement.setInt(4, sighting.getLocation().getId());
            return statement;
        }, keyHolder);

        sighting.setId(keyHolder.getKey().intValue());
        return sighting;
    }

    private void insertHero(Sighting sighting) {
        String sql = "SELECT * FROM hero JOIN sighting ON hero.HeroPK = sighting.HeroPK WHERE sighting.SightingPK = ?";
        Hero hero = jdbc.queryForObject(sql, new HeroDaoDB.HeroMapper(), sighting.getId());
        sighting.setHero(hero);
    }

    private void insertLocation(Sighting sighting) {
        String sql = "SELECT * FROM location JOIN sighting ON location.LocationPK = sighting.LocationPK WHERE sighting.SightingPK = ?";
        Location location = jdbc.queryForObject(sql, new LocationDaoDB.LocationMapper(), sighting.getId());
        sighting.setLocation(location);
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
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        final String sql = "SELECT * FROM sighting WHERE SightingDate = ?;";
        List<Sighting> sightings = jdbc.query(sql, new SightingMapper(), date);
        for (Sighting sighting: sightings) {
            insertLocation(sighting);
            insertHero(sighting);
        }
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        final String sql = "SELECT * FROM sighting WHERE LocationPK = ?;";
        List<Sighting> sightings =  jdbc.query(sql, new SightingMapper(), location.getId());
        for (Sighting sighting: sightings) {
            insertLocation(sighting);
            insertHero(sighting);
        }
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        final String sql = "SELECT * FROM sighting WHERE HeroPK = ?;";
        List<Sighting> sightings =  jdbc.query(sql, new SightingMapper(), hero.getId());
        for (Sighting sighting: sightings) {
            insertLocation(sighting);
            insertHero(sighting);
        }
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("SightingPK"));
            sighting.setDate(rs.getTimestamp("SightingDate").toLocalDateTime());
            sighting.setDescription(rs.getString("Description"));
            return sighting;
        }
    }
}
