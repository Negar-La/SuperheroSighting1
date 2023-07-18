
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Organization;
import java.util.List;

/**
 *
 * @author jtriolo
 */
public interface HeroDao {
    
    Hero getHeroByID(int id);
    List<Hero> getAllHeros();
    Hero addHero(Hero hero);
    void updateHero(Hero hero);
    void deleteHeroByID(int id);
  
    
    List<Hero> getHerosByLocation(Location location);
    List<Hero> getHerosByOrganization(Organization organization);    
    
}
