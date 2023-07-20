package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public String addHero(Hero hero, HttpServletRequest request) {
        String powerID = request.getParameter("powerID");

        hero.setPower(powerDao.getPowerByID(Integer.parseInt(powerID)));
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

        model.addAttribute("hero", hero);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroByID(id);

        hero.setName(request.getParameter("name"));
        hero.setType(request.getParameter("type"));
        hero.setDescription(request.getParameter("description"));

        heroDao.updateHero(hero);

        return "redirect:/heroes";
    }
}
