package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.*;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.LocationService;
import com.we.SuperHeroSightings.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HeroController {

    @Autowired
    HeroDao heroDao;
    HeroService heroService;

    @Autowired
    LocationDao locationDao;
    LocationService locationService;

    @Autowired
    OrganizationDao organizationDao;
    OrganizationService organizationService;

    @Autowired
    PowerDao powerDao;
    PowerService powerService;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroService.getAllHeroes();
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("heroes", heroes);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "heroes";
    }


    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        String powerId = request.getParameter("powerID");

        Hero hero = new Hero();
        hero.setPower(powerService.getPowerByID(Integer.parseInt(powerId)));

        hero.setDescription(description);
        hero.setName(name);
        hero.setType(type);

        heroService.addHero(hero);

        return "redirect:/heroes";
    }



    @GetMapping("heroDetails")
    public String heroDetails(Integer id, Model model) {
        Hero hero = heroService.getHeroByID(id);
        model.addAttribute("hero", hero);
        return "heroDetails";
    }


    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            heroService.deleteHeroByID(id);
            return "redirect:/heroes";
        } catch (Exception ex) {

        }
        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroService.getHeroByID(id);

        List<Power> powers = powerService.getAllPowers();
        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);

        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(Hero hero, HttpServletRequest request) {
        String powerId = request.getParameter("powerID");

        hero.setPower(powerService.getPowerByID(Integer.parseInt(powerId)));

        heroService.updateHero(hero);

        return "redirect:/heroes";
    }


}
