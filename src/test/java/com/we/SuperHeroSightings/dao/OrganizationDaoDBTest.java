
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import com.we.SuperHeroSightings.entities.Sighting;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author jtriolo
 */
@SpringBootTest
public class OrganizationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        sightings.forEach(sighting -> {
            sightingDao.deleteSightingByID(sighting.getId());
        });

        List<Hero> heros = heroDao.getAllHeros();
        heros.forEach(hero -> {
            heroDao.deleteHeroByID(hero.getId());
        });
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        organizations.forEach(organization -> {
            organizationDao.deleteOrganizationByID(organization.getId());
        });
        
        List<Location> locations = locationDao.getAllLocations();
        locations.forEach(location -> {
            locationDao.deleteLocationByID(location.getId());
        });
        
        List<Power> powers = powerDao.getAllPowers();
        powers.forEach(power -> {
            powerDao.deletePowerByID(power.getId());
        });
    }


    /**
     * Test of getOrganizationByID method & addOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testAddAndGetOrganizationByID() {
        Organization organization = new Organization();
        organization.setName("Federation");
        organization.setDescription("Heros for Justice");
        organization.setAddress("444 Main St, Queensplace, NY");
        organization.setType("Hero");
        organization.setPhone("222-333-4455");
        organization.setContact("Head Honcho");
        organization.setMembers(new ArrayList<>());
        organization = organizationDao.addOrganization(organization);
        
        Organization added = organizationDao.getOrganizationByID(organization.getId());
        assertEquals(organization, added);       
    }

    
    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization organization1 = new Organization();
        organization1.setName("Federation For Good");
        organization1.setDescription("Heros for Justice");
        organization1.setAddress("444 Main St, Queensplace, NY");
        organization1.setType("Hero");
        organization1.setPhone("222-333-4455");
        organization1.setContact("Head Good Honcho");
        organization1.setMembers(new ArrayList<>());
        organization1 = organizationDao.addOrganization(organization1);
        
        Organization organization2 = new Organization();
        organization2.setName("Federation For Evil");
        organization2.setDescription("Heros for Chaos");
        organization2.setAddress("444 Main St, Jokersplace, NY");
        organization2.setType("Villian");
        organization2.setPhone("555-333-7755");
        organization2.setContact("Head Evil Honcho");
        organization2.setMembers(new ArrayList<>());
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization1));
        assertTrue(organizations.contains(organization2));       
    }


    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Organization organization = new Organization();
        organization.setName("Federation");
        organization.setDescription("Heros for Justice");
        organization.setAddress("444 Main St, Queensplace, NY");
        organization.setType("Hero");
        organization.setPhone("222-333-4455");
        organization.setContact("Head Honcho");
        organization.setMembers(new ArrayList<>());
        organization = organizationDao.addOrganization(organization);
        
        Organization updated = organizationDao.getOrganizationByID(organization.getId());
        assertEquals(organization, updated);

        organization.setDescription("Heros for Justice for All");
        organizationDao.updateOrganization(organization);
        assertNotEquals(organization, updated);

        updated = organizationDao.getOrganizationByID(organization.getId());
        assertEquals(organization, updated);        
    }

    
    /**
     * Test of deleteOrganizationByID method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganizationByID() {
        Organization organization = new Organization();
        organization.setName("Federation");
        organization.setDescription("Heros for Justice");
        organization.setAddress("444 Main St, Queensplace, NY");
        organization.setType("Hero");
        organization.setPhone("222-333-4455");
        organization.setContact("Head Honcho");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        
        Power power = new Power();
        power.setName("Super Powers");
        power.setDescription("Extra strength, speed and flying");
        power = powerDao.addPower(power);
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setDescription("From Kyrpton");
        hero.setType("Hero");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        heroDao.addHero(hero);  

        organizationDao.deleteOrganizationByID(organization.getId());
        
        Organization deleted = organizationDao.getOrganizationByID(organization.getId());
        assertNull(deleted);        
    }

    
    /**
     * Test of getOrganizationsByHero method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetOrganizationsByHero() {        
        Organization organization1 = new Organization();
        organization1.setName("Federation");
        organization1.setDescription("Heros for Justice");
        organization1.setAddress("444 Main St, Queensplace, NY");
        organization1.setType("Hero");
        organization1.setPhone("222-333-4455");
        organization1.setContact("Head Honcho");
        organization1 = organizationDao.addOrganization(organization1);
        
        Organization organization2 = new Organization();
        organization2.setName("Justice Fleet");
        organization2.setDescription("Fleet for Justice");
        organization2.setAddress("555 Main St, Kingsplace, NY");
        organization2.setType("Hero");
        organization2.setPhone("333-222-4477");
        organization2.setContact("Fleet Captain");
        organization2 = organizationDao.addOrganization(organization2);
        
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);
        organizations.add(organization2);
        
        Power power = new Power();
        power.setName("Super Powers");
        power.setDescription("Extra strength, speed and flying");
        power = powerDao.addPower(power);
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setDescription("From Kyrpton");
        hero.setType("Hero");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        heroDao.addHero(hero);

 
        List<Organization> heroOrganizations = organizationDao.getOrganizationsByHero(hero);
        assertEquals(2, heroOrganizations.size());
        assertEquals("Federation", heroOrganizations.get(0).getName());
        assertEquals("Justice Fleet", heroOrganizations.get(1).getName());        
    }
    
}
