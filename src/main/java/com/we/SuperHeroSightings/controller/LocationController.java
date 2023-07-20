package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LocationController {
    @Autowired
    LocationDao locationDao;
    @Autowired
    HeroDao heroDao;
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    PowerDao powerDao;
    @Autowired
    SightingDao sightingDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        String locationName = request.getParameter("name");
        String description = request.getParameter("description");
        String locationAddress = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        Location location = new Location();
        location.setName(locationName);
        location.setDescription(description);
        location.setAddress(locationAddress);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        locationDao.addLocation(location);

        return "redirect:/locations";
    }
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationByID(id);

        return "redirect:/locations";
    }
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationByID(id);

        model.addAttribute("location", location);
        return "editLocation";
    }
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationByID(id);

        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));

        locationDao.updateLocation(location);

        return "redirect:/locations";
    }

}
