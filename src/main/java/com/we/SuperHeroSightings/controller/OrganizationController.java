package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.*;
import com.we.SuperHeroSightings.entities.Hero;
import com.we.SuperHeroSightings.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
public class OrganizationController {
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

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Hero> heroes = heroDao.getAllHeros();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        model.addAttribute("heroes", heroes);
        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("id");

        List<Hero> heroes = new ArrayList<>();
        for(String heroId: heroIds) {
            heroes.add(heroDao.getHeroByID(Integer.parseInt(heroId)));
        }


        String name = request.getParameter("OrganizationName");
        String type = request.getParameter("Type");
        String description = request.getParameter("Description");
        String address = request.getParameter("OrganizationAddress");
        String phone = request.getParameter("Phone");
        String contact = request.getParameter("ContactInfo");

        Organization organization = new Organization();
        organization.setName(name);
        organization.setType(type);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setPhone(phone);
        organization.setContact(contact);
        organization.setMembers(heroes);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if(violations.isEmpty()) {
            organizationDao.addOrganization(organization);
        }
        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationByID(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }


    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organizationDao.deleteOrganizationByID(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editCourse(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationByID(id);
        List<Hero> heroes = heroDao.getAllHeros();

        model.addAttribute("organization", organization);
        model.addAttribute("heroes", heroes);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model) {

        String[] heroIds = request.getParameterValues("id");

        List<Hero> heroes = new ArrayList<>();
        if(heroIds != null) {
            for(String heroId : heroIds) {
                heroes.add(heroDao.getHeroByID(Integer.parseInt(heroId)));
            }
        } else {
            FieldError error = new FieldError("organization", "heroes", "Must include one hero");
            result.addError(error);
        }

        organization.setMembers(heroes);

        if(result.hasErrors()) {
            model.addAttribute("heroes", heroDao.getAllHeros());
            model.addAttribute("organization", organization);
            return "editOrganization";
        }

        if(result.hasErrors()) {
            return "editOrganization";
        }
        organizationDao.updateOrganization(organization);

        return "redirect:/organizations";
    }
}
