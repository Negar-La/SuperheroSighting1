package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.HeroDao;
import com.we.SuperHeroSightings.dao.LocationDao;
import com.we.SuperHeroSightings.dao.SightingDao;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Location;
import com.we.SuperHeroSightings.entities.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SightingController {
    @Autowired
    HeroDao heroDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SightingDao sightingDao;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Hero> heroes = heroDao.getAllHeros();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

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
        sighting.setLocation(locationDao.getLocationByID(Integer.parseInt(locationId)));
        sighting.setDate(LocalDateTime.parse(datetime));
        sighting.setDescription(description);

        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingByID(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingByID(id);
        List<Hero> heroes = heroDao.getAllHeros();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Integer id, HttpServletRequest request) {
        String heroId = request.getParameter("heroID");
        String locationId = request.getParameter("locationID");
        String datetime = request.getParameter("date");
        String description = request.getParameter("description");

        Sighting sighting = sightingDao.getSightingByID(id);
        sighting.setHero(heroDao.getHeroByID(Integer.parseInt(heroId)));
        sighting.setLocation(locationDao.getLocationByID(Integer.parseInt(locationId)));
        sighting.setDate(LocalDateTime.parse(datetime));
        sighting.setDescription(description);

        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }

}
