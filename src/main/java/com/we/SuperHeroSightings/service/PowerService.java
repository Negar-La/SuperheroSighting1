package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Power;
import java.util.List;

/**
 *
 * @author marya
 */
public interface PowerService {

    public Power createPower(String name, String description);
    public Power validatePower(Power power );
    public Power getPowerByID(int id);

    public List<Power> getAllPowers();

    public Power addPower(Power power);

    public void updatePower(Power power);

    public void deletePowerByID(int id);
}