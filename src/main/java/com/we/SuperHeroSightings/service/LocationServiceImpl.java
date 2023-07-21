package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.LocationDao;
import com.we.SuperHeroSightings.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocationServiceImpl implements LocationService{
    @Autowired
    private LocationDao locationDao;

    @Override
    public Location addNewLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location getLocationById(int id) {
            return locationDao.getLocationByID(id);
    }

    @Override
    public Location updateLocationData(Location location) {
        locationDao.updateLocation(location);
        return location;
    }

    @Override
    public void deleteLocation(int id) {
        locationDao.deleteLocationByID(id);
    }
}
