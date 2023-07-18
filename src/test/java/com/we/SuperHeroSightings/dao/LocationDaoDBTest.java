
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import com.we.SuperHeroSightings.entities.Sighting;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class LocationDaoDBTest {
    
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
    
    LocalDateTime date = LocalDateTime.now();
    
    
    public LocationDaoDBTest() {
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
     * Test of getLocationByID method & addLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocationByID() {
        Location location = new Location();
        location.setName("Downtown Main");
        location.setDescription("Downtown by Main St");
        location.setAddress("123 Main St, Kingsplace, NY");
        location.setLatitude("40.71455");
        location.setLongitude("-74.00712");
        location = locationDao.addLocation(location);
        
        Location added = locationDao.getLocationByID(location.getId());
        assertEquals(location, added);
    }

    
    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
        Location location1 = new Location();
        location1.setName("Downtown Main");
        location1.setDescription("Downtown by Main St");
        location1.setAddress("123 Main St, Kingsplace, NY");
        location1.setLatitude("40.71455");
        location1.setLongitude("-74.00712");
        location1 = locationDao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setName("University Main");
        location2.setDescription("Main St by the University");
        location2.setAddress("456 University Ave, Kingsplace, NY");
        location2.setLatitude("41.71465");
        location2.setLongitude("-75.07912");
        location2 = locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));
    }

    
    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Downtown Main");
        location.setDescription("Downtown by Main St");
        location.setAddress("123 Main St, Kingsplace, NY");
        location.setLatitude("40.71455");
        location.setLongitude("-74.00712");
        location = locationDao.addLocation(location);

        Location updated = locationDao.getLocationByID(location.getId());
        assertEquals(location, updated);

        location.setName("Main Downtown");
        locationDao.updateLocation(location);
        assertNotEquals(location, updated);

        updated = locationDao.getLocationByID(location.getId());
        assertEquals(location, updated);        
    }

    
    /**
     * Test of deleteLocationByID method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationByID() {
        Power power = new Power();
        power.setName("Super Powers");
        power.setDescription("Extra strength, speed and flying");
        power = powerDao.addPower(power);
        
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
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setDescription("From Kyrpton");
        hero.setType("Hero");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);
        
        Location location = new Location();
        location.setName("Downtown Main");
        location.setDescription("Downtown by Main St");
        location.setAddress("123 Main St, Kingsplace, NY");
        location.setLatitude("40.71455");
        location.setLongitude("-74.00712");
        location = locationDao.addLocation(location);
        
        Sighting sighting = new Sighting();
        sighting.setDate(date);
        sighting.setDescription("Hero saved kitten");
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        locationDao.deleteLocationByID(location.getId());
        
        Sighting deletedSighting = sightingDao.getSightingByID(sighting.getId());
        assertNull(deletedSighting);

        Location deletedLocation = locationDao.getLocationByID(location.getId());
        assertNull(deletedLocation);
    }

    
    /**
     * Test of getLocationsByHero method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsByHero() {
        Power power = new Power();
        power.setName("Super Powers");
        power.setDescription("Extra strength, speed and flying");
        power = powerDao.addPower(power);
        
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
        
        Hero hero = new Hero();
        hero.setName("Superman");
        hero.setDescription("From Kyrpton");
        hero.setType("Hero");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);
        
        Location location1 = new Location();
        location1.setName("Downtown Main");
        location1.setDescription("Downtown by Main St");
        location1.setAddress("123 Main St, Kingsplace, NY");
        location1.setLatitude("40.71455");
        location1.setLongitude("-74.00712");
        location1 = locationDao.addLocation(location1);
        
        Location location2 = new Location();
        location2.setName("University Main");
        location2.setDescription("Main St by the University");
        location2.setAddress("456 University Ave, Kingsplace, NY");
        location2.setLatitude("41.71465");
        location2.setLongitude("-75.07912");
        location2 = locationDao.addLocation(location2);
        
        Sighting sighting1 = new Sighting();
        sighting1.setDate(date);
        sighting1.setDescription("Hero saved kitten");
        sighting1.setHero(hero);
        sighting1.setLocation(location1);
        sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(date);
        sighting2.setDescription("Hero stopped robbery");
        sighting2.setHero(hero);
        sighting2.setLocation(location2);
        sightingDao.addSighting(sighting2);
        
        List<Location> locations = locationDao.getLocationsByHero(hero);
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));       
    }
    
}
