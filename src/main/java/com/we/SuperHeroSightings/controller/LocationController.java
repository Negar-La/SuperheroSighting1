package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class LocationController {
    @Autowired
    LocationService locationService;
    Set<ConstraintViolation<Location>> violations = new HashSet<>();


    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
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

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if (violations.isEmpty()) {
            locationService.addNewLocation(location);
        }

        return "redirect:/locations";

    }


    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationService.deleteLocation(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);

        model.addAttribute("location", location);
        model.addAttribute("errors", violations);
        return "editLocation";
    }
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationService.getLocationById(id);

        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setLatitude(request.getParameter("latitude"));
        location.setLongitude(request.getParameter("longitude"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if (violations.isEmpty()) {
            locationService.updateLocationData(location);
        }
        return "redirect:/locations";
    }

//    @PostMapping("editLocation")
//    public String performEditLocation(@Valid Location location, BindingResult result, HttpServletRequest request, Model model) {
//        String locationId = request.getParameter("id");
//
//        Location location = locationService.getLocationById(id);
//        location.setName(request.getParameter("name"));
//
//
//        location.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
//
//        List<Location> locations = new ArrayList<>();
//        if(locationId != null) {
//            for(String locationId : locationIds)
//    }
//}else{
//        FieldError error=new FieldError("location","Must include one location");
//        result.addError(error);
//        }
//        location.set
//
//        if(result.hasErrors()) {
//            return "editLocation";
//        }
//        locationService.updateLocationData(location);
//        return "redirect:/locations";
//    }

//    @GetMapping("locations")
//    public String displayLocations(Model model) {
//        List<Location> locations = locationDao.getAllLocations();
//        model.addAttribute("locations", locations);
//        return "locations";
//    }
//    @PostMapping("addLocation")
//    public String addLocation(HttpServletRequest request){
//        String locationName = request.getParameter("name");
//        String description = request.getParameter("description");
//        String locationAddress = request.getParameter("address");
//        String latitude = request.getParameter("latitude");
//        String longitude = request.getParameter("longitude");
//
//        Location location = new Location();
//        location.setName(locationName);
//        location.setDescription(description);
//        location.setAddress(locationAddress);
//        location.setLatitude(latitude);
//        location.setLongitude(longitude);
//
//        locationDao.addLocation(location);
//
//        return "redirect:/locations";
//    }
//    @GetMapping("deleteLocation")
//    public String deleteLocation(HttpServletRequest request) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        locationDao.deleteLocationByID(id);
//
//        return "redirect:/locations";
//    }
//    @GetMapping("editLocation")
//    public String editLocation(HttpServletRequest request, Model model) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Location location = locationDao.getLocationByID(id);
//
//        model.addAttribute("location", location);
//        return "editLocation";
//    }
//    @PostMapping("editLocation")
//    public String performEditLocation(HttpServletRequest request) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Location location = locationDao.getLocationByID(id);
//
//        location.setName(request.getParameter("name"));
//        location.setDescription(request.getParameter("description"));
//        location.setAddress(request.getParameter("address"));
//        location.setLatitude(request.getParameter("latitude"));
//        location.setLongitude(request.getParameter("longitude"));
//
//        locationDao.updateLocation(location);
//
//        return "redirect:/locations";
//    }

}
