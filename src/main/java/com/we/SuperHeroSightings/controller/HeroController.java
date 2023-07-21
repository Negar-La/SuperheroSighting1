package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.*;
import com.we.SuperHeroSightings.service.HeroService;
import com.we.SuperHeroSightings.service.OrganizationService;
import com.we.SuperHeroSightings.service.PowerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HeroController {

    @Autowired
    HeroService heroService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PowerService powerService;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();



    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroService.getAllHeros();
        List<Power> powers = powerService.getAllPowers();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("heroes", heroes);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);

        model.addAttribute("errors", violations);
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

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        if (violations.isEmpty()) {
            heroService.addHero(hero);
        }

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
    public String performEditHero(@Valid Hero hero, BindingResult result, HttpServletRequest request, Model model) {
        String powerId = request.getParameter("powerID");

        hero.setPower(powerService.getPowerByID(Integer.parseInt(powerId)));

        if(result.hasErrors()) {
            model.addAttribute("powers", powerService.getAllPowers());
            return "editHero";
        }

            heroService.updateHero(hero);


        return "redirect:/heroes";
    }


}
