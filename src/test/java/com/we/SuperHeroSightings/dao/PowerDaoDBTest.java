
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
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
public class PowerDaoDBTest {
    
    @Autowired
    PowerDao powerDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    public PowerDaoDBTest() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Power> powers = powerDao.getAllPowers();
        powers.forEach(power -> {
            powerDao.deletePowerByID(power.getId());
        });
        
        List<Hero> heros = heroDao.getAllHeros();
        heros.forEach(hero -> {
            heroDao.deleteHeroByID(hero.getId());
        });
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        organizations.forEach(organization -> {
            organizationDao.deleteOrganizationByID(organization.getId());
        });
    }


    /**
     * Test of getPowerByID method & addPower method, of class PowerDaoDB.
     */
    @Test
    public void testAddAndGetPowerByID() {
        Power power = new Power();
        power.setName("Super Powers");
        power.setDescription("Extra strength, speed and flying");
        power = powerDao.addPower(power);
        
        Power added = powerDao.getPowerByID(power.getId());
        assertEquals(power, added);
    }

    
    /**
     * Test of getAllPowers method, of class PowerDaoDB.
     */
    @Test
    public void testGetAllPowers() {
        Power power1 = new Power();
        power1.setName("Super Powers");
        power1.setDescription("Extra strength, speed and flying");
        power1 = powerDao.addPower(power1);
        
        Power power2 = new Power();
        power2.setName("Speed");
        power2.setDescription("Extra speed and flying");
        power2 = powerDao.addPower(power2);
        
        Power power3 = new Power();
        power3.setName("Normal");
        power3.setDescription("No Superpower, but highly skilled");
        power3 = powerDao.addPower(power3);
        
        List<Power> powers = powerDao.getAllPowers();

        assertEquals(3, powers.size());
        assertTrue(powers.contains(power1));
        assertTrue(powers.contains(power2));
        assertTrue(powers.contains(power3));
    }


    /**
     * Test of updatePower method, of class PowerDaoDB.
     */
    @Test
    public void testUpdatePower() {
        Power power = new Power();
        power.setName("Super Powers");
        power.setDescription("Extra strength, speed and flying");
        power = powerDao.addPower(power);

        Power updated = powerDao.getPowerByID(power.getId());
        assertEquals(power, updated);

        power.setName("Super Speed");
        power.setDescription("Very fast");
        powerDao.updatePower(power);
        assertNotEquals(power, updated);

        updated = powerDao.getPowerByID(power.getId());
        assertEquals(power, updated);  
    }

    
    /**
     * Test of deletePowerByID method, of class PowerDaoDB.
     */
    @Test
    public void testDeletePowerByID() {
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
        
        Power deleted = powerDao.getPowerByID(power.getId());
        assertEquals(power, deleted);

        powerDao.deletePowerByID(power.getId());

        deleted = powerDao.getPowerByID(power.getId());
        assertNull(deleted);

        hero = heroDao.getHeroByID(hero.getId());
        assertNull(hero.getPower());
    }
    
}
