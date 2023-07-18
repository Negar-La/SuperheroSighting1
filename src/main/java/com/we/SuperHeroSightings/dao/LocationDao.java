
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import java.util.List;

/**
 *
 * @author jtriolo
 */
public interface LocationDao {
    
    Location getLocationByID(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationByID(int id);
    
    List<Location> getLocationsByHero(Hero hero);
    
}
