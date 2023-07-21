package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.SightingDao;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SightingServiceImpl implements SightingService{

    @Autowired
    SightingDao sightingDao;

    @Override
    public Sighting getSightingByID(int id) {
        return sightingDao.getSightingByID(id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public void updateSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void deleteSightingByID(int id) {
        sightingDao.deleteSightingByID(id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        return sightingDao.getSightingsByDate(date);
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        return sightingDao.getSightingsByLocation(location);
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        return sightingDao.getSightingsByHero(hero);
    }
}
