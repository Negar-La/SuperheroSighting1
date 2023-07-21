
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class HeroDaoDB implements HeroDao {
    
    @Autowired
    JdbcTemplate jdbc;


    @Override
    //gets a hero from the database based on the provided ID
    //connects to the "hero" table and retrieves the hero's information including its associated power
    public Hero getHeroByID(int id) {
        try {
            String sql = "SELECT * FROM hero WHERE HeroPK = ?";
            Hero hero = jdbc.queryForObject(sql, new HeroMapper(), id);
            Power power = setPowerForHero(hero);
            hero.setPower(power);
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
            return hero;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private Power setPowerForHero(Hero hero) {
        try {
            String sql = "SELECT * FROM power JOIN hero ON hero.PowerPK = power.PowerPK WHERE hero.HeroPK = ?";
            Power power = jdbc.queryForObject(sql, new PowerDaoDB.PowerMapper(), hero.getId());
            return power;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    //gets all heroes from the database
    //connects to the "hero" table and fetches all the hero records present in the table
    public List<Hero> getAllHeros() {
        String sql = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper());
        Power power;
        for (Hero hero: heroes) {
            power = setPowerForHero(hero);
            hero.setPower(power);
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
        }
        return heroes;
    }

    @Override
    @Transactional
    //adds a new hero to the database
    //connects to the "hero" table and inserts a new record with the new hero's information + power
    public Hero addHero(Hero hero) {
        String sql = "INSERT INTO hero (HeroName, Type, Description, PowerPK) VALUES (?, ?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, hero.getName());
            ps.setString(2, hero.getType());
            ps.setString(3, hero.getDescription());
            ps.setInt(4, hero.getPower().getId());
            return ps;
        }, keyHolder);
        hero.setId(keyHolder.getKey().intValue());
        //Navdeep added condition to deal with null value for insertorganization
        if (hero.getOrganizations() != null) {
            insertHeroOrganization(hero);
        }
//        insertHeroOrganization(hero);
        return hero;
    }
    private void insertHeroOrganization(Hero hero){
        for (Organization organization : hero.getOrganizations()){
            jdbc.update("INSERT INTO heroorganization(heroPK, organizationPK) VALUES (?, ?);",
                    hero.getId(),
                    organization.getId());
        }
    }


    private List<Organization> getOrganizationsForHero(int id){
        return jdbc.query("SELECT o.* FROM organization o INNER JOIN heroorganization ho ON o.OrganizationPK = ho.OrganizationPK INNER JOIN hero h ON ho.HeroPK = h.HeroPK WHERE ho.HeroPK = ?"
                , new OrganizationDaoDB.OrganizationMapper(), id);
    }

    @Override
    //updates an existing hero in the database
    //connects to the "hero" table and changes the record with the new hero's information + power.
    public void updateHero(Hero hero) {
        String sql = "UPDATE hero SET HeroName = ?, Type = ?, Description = ?, PowerPK = ? WHERE HeroPK = ?";
        jdbc.update(sql, hero.getName(), hero.getType(), hero.getDescription(), hero.getPower().getId(), hero.getId());
    }

    @Override
    //deletes a hero from the database based on the provided ID
    //connects to the "hero" table and removes the record associated with the provided ID
    public void deleteHeroByID(int id) {
        String sql1 = "DELETE FROM sighting WHERE HeroPK = ?;";
        String sql2 = "DELETE FROM heroorganization WHERE HeroPK = ?;";
        String sql = "DELETE FROM hero WHERE HeroPK = ?;";
        jdbc.update(sql1, id);
        jdbc.update(sql2, id);
        jdbc.update(sql, id);
    }

    @Override
    //gets heroes from the database based on the provided location
    //connects to the "hero" and "sighting" tables, joins them together and gets the heroes that have sightings associated with the given location
    public List<Hero> getHerosByLocation(Location location) {
        String sql = "SELECT h.* FROM hero h JOIN sighting s ON h.HeroPK = s.HeroPK WHERE s.LocationPK = ?";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper(), location.getId());
        Power power;
        for (Hero hero: heroes) {
            power = setPowerForHero(hero);
            hero.setPower(power);
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
        }
        return heroes;
    }

    @Override
    //gets heroes from the database based on the given organization
    //connects to the "hero" and "heroorganization" tables + joins them together + retrieves the heroes that are associated with the given organization
    public List<Hero> getHerosByOrganization(Organization organization) {
        String sql = "SELECT h.* FROM hero h JOIN heroorganization ho ON h.HeroPK = ho.HeroPK WHERE ho.OrganizationPK = ?";
        List<Hero> heroes = jdbc.query(sql, new HeroMapper(), organization.getId());
        Power power;
        for (Hero hero: heroes) {
            power = setPowerForHero(hero);
            hero.setPower(power);
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
        }
        return heroes;
    }

    public static class HeroMapper implements RowMapper<Hero> {
        @Override
        //helper class that maps the result set from the database to Hero objects
        //maps the retrieved data to the appropriate properties of the Hero DTO class 
        public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("HeroPK"));
            hero.setName(rs.getString("HeroName"));
            hero.setType(rs.getString("Type"));
            hero.setDescription(rs.getString("Description"));
            return hero;
        }
    }
}
