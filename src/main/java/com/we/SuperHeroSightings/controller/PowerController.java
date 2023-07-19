package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.PowerDao;
import com.we.SuperHeroSightings.entities.Power;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PowerController {

    @Autowired
    PowerDao powerDao;

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(String name,String description) {
        Power power = new Power();
        power.setName(name);
        power.setDescription(description);
        powerDao.addPower(power);

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
        model.addAttribute("Power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String editPower(Power power) {
        powerDao.updatePower(power);
        return "redirect:/powers";
    }
}
