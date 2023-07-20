package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.PowerDao;
import com.we.SuperHeroSightings.entities.Power;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowerServiceImpl implements PowerService {

    @Autowired
    PowerDao powerDao;

    @Override
    public Power createPower(String name, String description) {
        Power power = new Power();

        power.setName(name);
        power.setDescription(description);

        return power;
    }

    @Override
    public Power getPowerByID(int id) {
        return powerDao.getPowerByID(id);
    }

    @Override
    public List<Power> getAllPowers() {
        return powerDao.getAllPowers();
    }

    @Override
    public Power addPower(Power power) {
        return powerDao.addPower(power);
    }

    @Override
    public void updatePower(Power power) {

        powerDao.updatePower(power);
    }

    @Override
    public void deletePowerByID(int id) {
        powerDao.deletePowerByID(id);
    }

    @Override
    public Power validatePower(Power power) {
        if (power.getName().isBlank() || power.getDescription().isBlank()) {

            return null;
        }
        return power;
    }
}
