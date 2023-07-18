
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
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
public class HeroDaoDB implements HeroDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Hero> getAllHeros() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Hero addHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteHeroByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Hero> getHerosByLocation(Location location) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Hero> getHerosByOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
   

    
}
