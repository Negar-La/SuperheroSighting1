package com.we.SuperHeroSightings.service;

import com.we.SuperHeroSightings.dao.LocationDao;
import com.we.SuperHeroSightings.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        //if data access exception is thrown, set to course not found
        try {
            return locationDao.getLocationByID(id);
        } catch (DataAccessException ex) {
            Location lnf = new Location();
            lnf.setName("Location Not Found");
            lnf.setDescription("Location Not Found");
            lnf.setAddress("Location Not Found");
            lnf.setLatitude("Location Not Found");
            lnf.setLongitude("Location Not Found");
            return lnf;
        }
    }

    @Override
    public Location updateLocationData(Location location) {
        locationDao.updateLocation(location);
        return location;
        //if id does not equal object id, set everything to ID's do not match
//        if (location.getId() != id) {
//            location.setName("IDs do not match, location not updated.");
//            location.setDescription("IDs do not match, location not updated.");
//            location.setAddress("IDs do not match, location not updated.");
//            location.setLatitude("IDs do not match, location not updated.");
//            location.setLongitude("IDs do not match, location not updated.");
//        } else {
//            locationDao.updateLocation(location);
//        }
//        return location;
    }

    @Override
    public void deleteLocation(int id) {
        //After deletion, print a line to the server console: "LocationID: + id + " deleted".
        locationDao.deleteLocationByID(id);
    }
}
