package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Power.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                int powerId = Integer.parseInt(text);
                Power power = powerDao.getPowerByID(powerId);
                setValue(power);
            }
        });
    }

    @PostMapping("/addHero")
    public String addHero(@ModelAttribute("hero") @Valid Hero hero, BindingResult result, @RequestParam("power") Power power, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("powers", powerDao.getAllPowers());
            return "add-hero-form";
        }

        hero.setPower(power);

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
    public String performEditHero(Hero hero, HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        hero.setName(request.getParameter("name"));
        hero.setType(request.getParameter("type"));
        hero.setDescription(request.getParameter("description"));

        heroDao.updateHero(hero);


        return "redirect:/heroes";
    }
}
