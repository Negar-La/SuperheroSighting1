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
        model.addAttribute("sightings", sightings);
        return "sightings";
    }

    //need to make sure parse is not causing errors for integer and date
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String date = request.getParameter("date");
        LocalDateTime parsedDate = LocalDateTime.parse(date); //could cause error
        String description = request.getParameter("description");
        String hero = request.getParameter("heroId");
        String location = request.getParameter("locationId");
        int heroId = Integer.parseInt(hero); //could cause error
        int locationId = Integer.parseInt(location); // could cause error

        Hero heroObject = heroDao.getHeroByID(heroId);
        Location locationObject = locationDao.getLocationByID(locationId);

        Sighting sighting = new Sighting();
        sighting.setDate(parsedDate);
        sighting.setDescription(description);
        sighting.setHero(heroObject);
        sighting.setLocation(locationObject);

        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSightingByID(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingByID(id);

        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingByID(id);

        sighting.setDate(LocalDateTime.parse(request.getParameter("date")));
        sighting.setDescription(request.getParameter("description"));
        sighting.setHero(heroDao.getHeroByID(Integer.parseInt(request.getParameter("heroId"))));
        sighting.setLocation(locationDao.getLocationByID(Integer.parseInt(request.getParameter("locationId"))));

        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }

}
