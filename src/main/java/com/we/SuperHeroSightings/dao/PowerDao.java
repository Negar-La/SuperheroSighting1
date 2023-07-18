
package com.we.SuperHeroSightings.dao;

import com.we.SuperHeroSightings.entities.Power;
import java.util.List;

/**
 *
 * @author jtriolo
 */
public interface PowerDao {
    
    Power getPowerByID(int id);
    List<Power> getAllPowers();
    Power addPower(Power power);
    void updatePower(Power power);
    void deletePowerByID(int id);
    
}
