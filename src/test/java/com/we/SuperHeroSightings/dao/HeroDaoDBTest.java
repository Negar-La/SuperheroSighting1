
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author jtriolo
 */
@SpringBootTest
public class HeroDaoDBTest {
    
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
    
    public HeroDaoDBTest() {
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
     * Test of getHeroByID method & addHero method, of class HeroDaoDB.
     */
    @Test
    public void testAddAndGetHeroByID() {
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
        
        Hero added = heroDao.getHeroByID(hero.getId());
        assertEquals(hero, added);        
    }
    
    
    @Test
    public void testAddHeroWithMissingField(){
         DataIntegrityViolationException thrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {             
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
            //hero.setName("Superman");  MISSING WILL THROW EXCEPTION
            hero.setDescription("From Kyrpton");
            hero.setType("Hero");
            hero.setPower(power);
            hero.setOrganizations(organizations);
            hero = heroDao.addHero(hero); 
	}, "DataIntegrityViolationException was thrown");
    }
    
    

    
    /**
     * Test of getAllHeros method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeros() {
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
        
        Hero hero1 = new Hero();
        hero1.setName("Superman");
        hero1.setDescription("From Kyrpton");
        hero1.setType("Hero");
        hero1.setPower(power);
        hero1.setOrganizations(organizations);
        hero1 = heroDao.addHero(hero1);
        
        Hero hero2 = new Hero();
        hero2.setName("Powerman");
        hero2.setDescription("Powerful punches");
        hero2.setType("Hero");
        hero2.setPower(power);
        hero2.setOrganizations(organizations);
        hero2 = heroDao.addHero(hero2);
        
        List<Hero> heros = heroDao.getAllHeros();

        assertEquals(2, heros.size());
        assertTrue(heros.contains(hero1));
        assertTrue(heros.contains(hero2));
    }


    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
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
        hero.setDescription("From Krypton");
        hero.setType("Hero");
        hero.setPower(power);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);
        
        Hero updated = heroDao.getHeroByID(hero.getId());
        assertEquals(hero, updated);

        hero.setDescription("Came to Earth from Krypton");
        heroDao.updateHero(hero);
        assertNotEquals(hero, updated);

        updated = heroDao.getHeroByID(hero.getId());
        assertEquals(hero, updated);   
    }

    
    /**
     * Test of deleteHeroByID method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHeroByID() {
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
        sightingDao.addSighting(sighting);
        
        Hero deleted = heroDao.getHeroByID(hero.getId());
        assertEquals(hero, deleted);
        
        heroDao.deleteHeroByID(hero.getId());
        
        deleted = heroDao.getHeroByID(hero.getId());
        assertNull(deleted);        
    }

    
    /**
     * Test of getHerosByLocation method, of class HeroDaoDB.
     */
    @Test
    public void testGetHerosByLocation() {
        Location location = new Location();
        location.setName("Downtown Main");
        location.setDescription("Downtown by Main St");
        location.setAddress("123 Main St, Kingsplace, NY");
        location.setLatitude("40.71455");
        location.setLongitude("-74.00712");
        location = locationDao.addLocation(location);
        
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
        
        Hero hero1 = new Hero();
        hero1.setName("Superman");
        hero1.setDescription("From Kyrpton");
        hero1.setType("Hero");
        hero1.setPower(power);
        hero1.setOrganizations(organizations);
        hero1 = heroDao.addHero(hero1);
        
        Hero hero2 = new Hero();
        hero2.setName("Powerman");
        hero2.setDescription("Powerful punches");
        hero2.setType("Hero");
        hero2.setPower(power);
        hero2.setOrganizations(organizations);
        hero2 = heroDao.addHero(hero2);
        
        Sighting sighting1 = new Sighting();
        sighting1.setDate(date);
        sighting1.setDescription("Hero saved kitten");
        sighting1.setHero(hero1);
        sighting1.setLocation(location);
        sightingDao.addSighting(sighting1);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(date);
        sighting2.setDescription("Hero stopped robbery");
        sighting2.setHero(hero2);
        sighting2.setLocation(location);
        sightingDao.addSighting(sighting2);
        
        List<Hero> heros = heroDao.getHerosByLocation(location);
        assertEquals(2, heros.size());
        
        assertTrue(heros.contains(hero1));
        assertTrue(heros.contains(hero2));        
    }

    
    /**
     * Test of getHerosByOrganization method, of class HeroDaoDB.
     */
    @Test
    public void testGetHerosByOrganization() {
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
        
        Hero hero1 = new Hero();
        hero1.setName("Superman");
        hero1.setDescription("From Kyrpton");
        hero1.setType("Hero");
        hero1.setPower(power);
        hero1.setOrganizations(organizations);
        hero1 = heroDao.addHero(hero1);
        
        Hero hero2 = new Hero();
        hero2.setName("Powerman");
        hero2.setDescription("Powerful punches");
        hero2.setType("Hero");
        hero2.setPower(power);
        hero2.setOrganizations(organizations);
        hero2 = heroDao.addHero(hero2);
        
        List<Hero> heros = heroDao.getHerosByOrganization(organization);
        assertEquals(2, heros.size());
        
        assertTrue(heros.contains(hero1));
        assertTrue(heros.contains(hero2));   
        
        
    }
    
}
