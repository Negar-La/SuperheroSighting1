package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.PowerDao;
import com.we.SuperHeroSightings.entities.Organization;
import com.we.SuperHeroSightings.entities.Power;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PowerController {

    @Autowired
    PowerDao powerDao;
    Set<ConstraintViolation<Power>> violations = new HashSet<>();

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("errors", violations);
        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        
        Power power = new Power();
        power.setName(name);
        power.setDescription(description);
        
        violations = validate.validate(power);

        if (violations.isEmpty()) {
           powerDao.addPower(power);
        }
      
        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        powerDao.deletePowerByID(id);
        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        Power power = powerDao.getPowerByID(id);
        model.addAttribute("power", power);
        return "editPower";
    }

//    @PostMapping("editPower")
//    public String performEditPower(HttpServletRequest request) {
//        int id = Integer.parseInt(request.getParameter("PowerPK"));
//        Power power = powerDao.getPowerByID(id);
//        power.setName(request.getParameter("name"));
//        power.setDescription(request.getParameter("description"));
//
//        powerDao.updatePower(power);
//        return "redirect:/powers";
//    }
    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result, HttpServletRequest request, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("power", power);
            return "editPower";
        }

        powerDao.updatePower(power);

        return "redirect:/powers";
    }
}
