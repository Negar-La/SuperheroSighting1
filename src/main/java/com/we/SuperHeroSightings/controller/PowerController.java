package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.entities.Power;
import com.we.SuperHeroSightings.service.PowerService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PowerController {

    @Autowired
    PowerService powerService;


    @GetMapping("powers")
    public String displayPowers( Model model) {
        List<Power> powers = powerService.getAllPowers();
        model.addAttribute("powers", powers);
         model.addAttribute("power", new Power());

        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(@Valid Power power, BindingResult result) {

        if (!result.hasErrors() ) {
            powerService.addPower(power);

        }
        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        powerService.deletePowerByID(id);
        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        Power power = powerService.getPowerByID(id);
        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid Power power, BindingResult result, HttpServletRequest request, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("power", power);
            return "editPower";
        }

        powerService.updatePower(power);

        return "redirect:/powers";
    }
}
