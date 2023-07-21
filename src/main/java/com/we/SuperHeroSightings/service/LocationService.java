package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.entities.Location;

import java.util.List;

public interface LocationService {
    Location addNewLocation(Location location);
    public List<Location> getAllLocations();
    Location getLocationById(int id);
    Location updateLocationData(Location location);
    public void deleteLocation(int id);
}
