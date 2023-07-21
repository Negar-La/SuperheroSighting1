package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;


import java.time.LocalDateTime;
import java.util.List;

@Controller
public class HeroController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    PowerDao powerDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDao.getAllHeros();
        List<Power> powers = powerDao.getAllPowers();
        List<Organization> organizations = organizationDao.getAllOrganizations();
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

        System.out.println("DEBUG : powerID is " + powerId );

        Hero hero = new Hero();
        hero.setPower(powerDao.getPowerByID(Integer.parseInt(powerId)));

        hero.setDescription(description);
        hero.setName(name);
        hero.setType(type);

        heroDao.addHero(hero);

        return "redirect:/heroes";
    }



    @GetMapping("heroDetails")
    public String heroDetails(Integer id, Model model) {
        Hero hero = heroDao.getHeroByID(id);
        model.addAttribute("hero", hero);
        return "heroDetails";
    }


    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            heroDao.deleteHeroByID(id);
            return "redirect:/heroes";
        } catch (Exception ex) {

        }
        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroByID(id);

        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);

        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(Integer id, HttpServletRequest request) {
        List <Power> powers = powerDao.getAllPowers();

        String powerId = request.getParameter("powerID");
        String description = request.getParameter("description");
        String name = request.getParameter("name");
        String type = request.getParameter("type");

        Hero hero = heroDao.getHeroByID(id);
        hero.setPower(powerDao.getPowerByID(Integer.parseInt(powerId)));
        hero.setDescription(description);
        hero.setName(name);
        hero.setType(type);

        heroDao.updateHero(hero);

        return "redirect:/heroes";
    }

}
