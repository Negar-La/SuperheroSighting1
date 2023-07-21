package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.HeroDao;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import com.we.SuperHeroSightings.service.LocationService;
import com.we.SuperHeroSightings.service.SightingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SightingController {
    @Autowired
    HeroDao heroDao;
    @Autowired
    LocationService locationService;
    @Autowired
    SightingService sightingService;

    Set<ConstraintViolation<Sighting>> errors = new HashSet<>();
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingService.getAllSightings();
        List<Hero> heroes = heroDao.getAllHeros();
        List<Location> locations = locationService.getAllLocations();

        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", errors);

        return "sightings";
    }

    //need to make sure parse is not causing errors for integer and date
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {

        String heroId = request.getParameter("heroID");
        String locationId = request.getParameter("locationID");
        String datetime = request.getParameter("date");
        String description = request.getParameter("description");

        Sighting sighting = new Sighting();
        sighting.setHero(heroDao.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        if ("".equals(datetime)) {
            sighting.setDate(null);
        } else {
            sighting.setDate(LocalDateTime.parse(datetime));
        }
        sighting.setDescription(description);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        errors = validate.validate(sighting);

        if(errors.isEmpty()) {
            sightingService.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingService.deleteSightingByID(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingService.getSightingByID(id);
        List<Hero> heroes = heroDao.getAllHeros();
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Integer id, HttpServletRequest request, Model model) {
        List<Hero> heroes = heroDao.getAllHeros();
        List<Location> locations = locationService.getAllLocations();

        String heroId = request.getParameter("heroID");
        String locationId = request.getParameter("locationID");
        String datetime = request.getParameter("date");
        String description = request.getParameter("description");

        Sighting sighting = sightingService.getSightingByID(id);
        sighting.setHero(heroDao.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(locationService.getLocationById(Integer.parseInt(locationId)));
        sighting.setDate(LocalDateTime.parse(datetime));
        sighting.setDescription(description);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        errors = validate.validate(sighting);

        if (errors.isEmpty()) {
            sightingService.updateSighting(sighting);
        } else {
            model.addAttribute("sighting", sighting);
            model.addAttribute("heroes", heroes);
            model.addAttribute("locations", locations);
            return "editSighting";
        }

        return "redirect:/sightings";
    }

}
