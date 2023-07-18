
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
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE OrganizationPK = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            organization.setMembers(getMembersForOrganization(id));
            return organization;

        } catch(DataAccessException ex) {
            return null;
        }
    }

    //     create a helper method to get SuperHeros for an organization:
    private List<Hero> getMembersForOrganization(int id){
        final String SELECT_MEMBERS_FOR_ORGANIZATION = "SELECT h.* FROM hero h "
                + "INNER JOIN heroorganization ho ON ho.HeroPK = h.HeroPK "
                + "WHERE ho.OrganizationPK = ?";
        return jdbc.query(SELECT_MEMBERS_FOR_ORGANIZATION, new HeroDaoDB.HeroMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        addSuperHerosToOrganization(organizations);

        return organizations;
    }

    //    a private helper method
    private void addSuperHerosToOrganization(List<Organization> organizations) {
        for (Organization organization: organizations){
            organization.setMembers(getMembersForOrganization(organization.getId()));
        }
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(OrganizationName, Type, Description," +
                " OrganizationAddress, Phone, ContactInfo) VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getContact());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);

        insertOrganizationSuperHero(organization);
        return organization;
    }

    private void insertOrganizationSuperHero(Organization organization) {

        final String INSERT_MEMBER = "INSERT INTO heroorganization (HeroPK, OrganizationPK) "
                + "VALUES(?,?)";
        for (Hero member : organization.getMembers()){
            jdbc.update(INSERT_MEMBER, organization.getId(), member.getId());
        }
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization "
                + "SET OrganizationName = ?, Type = ?, Description = ?, OrganizationAddress = ?, Phone = ?, ContactInfo = ?  WHERE OrganizationPK = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getPhone(),
                organization.getContact(),
                organization.getId());

//         deleting all the existing bridge table entries for the relationship.
        final String DELETE_ORGANIZATION_HERO = "DELETE FROM heroorganization "
                + "WHERE OrganizationPK = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, organization.getId());
        insertOrganizationSuperHero(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationByID(int id) {
        final String DELETE_ORGANIZATION_SUPERHERO = "DELETE FROM heroorganization "
                + "WHERE OrganizationPK = ?";
        jdbc.update(DELETE_ORGANIZATION_SUPERHERO, id);

        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE OrganizationPK = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_FOR_HERO = "SELECT * FROM organization o "
                + "JOIN heroorganization ho ON o.OrganizationPK = ho.OrganizationPK WHERE ho.HeroPK = ?";
        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_FOR_HERO,
                new OrganizationMapper(), hero.getId());

        addSuperHerosToOrganization(organizations);

        return organizations;
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("OrganizationPK"));
            organization.setName(rs.getString("OrganizationName"));
            organization.setType(rs.getString("Type"));
            organization.setDescription(rs.getString("Description"));
            organization.setAddress(rs.getString("OrganizationAddress"));
            organization.setPhone(rs.getString("Phone"));
            organization.setContact(rs.getString("ContactInfo"));
            return organization;
        }
    }
    


    
}
