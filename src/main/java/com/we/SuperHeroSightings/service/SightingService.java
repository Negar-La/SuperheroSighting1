package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;

import java.time.LocalDateTime;
import java.util.List;

public interface SightingService {
    Sighting getSightingByID(int id);
    List<Sighting> getAllSightings();
    List<Sighting> getLastTenSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingByID(int id);

    List<Sighting> getSightingsByDate(LocalDateTime date);
    List<Sighting> getSightingsByLocation(Location location);
    List<Sighting> getSightingsByHero(Hero hero);
}
