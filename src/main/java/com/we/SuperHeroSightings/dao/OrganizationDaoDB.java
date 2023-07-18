
package com.we.SuperHeroSightings.dao;


import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Organization> getAllOrganizations() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Organization addOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateOrganization(Organization organization) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteOrganizationByID(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
  
    


    
}
