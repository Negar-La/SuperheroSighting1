package com.we.SuperHeroSightings.controller;

import com.we.SuperHeroSightings.dao.PowerDao;
import com.we.SuperHeroSightings.entities.Power;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    public String addPower(String name, String description) {
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
        model.addAttribute("power", power);
        return "editPower";
    }

//    @PostMapping("editPower")
//    public String performEditPower(Power power) {
//        powerDao.updatePower(power);
//        return "redirect:/powers";
//    }

    @PostMapping("editPower")
    public String performEditPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerByID(id);
        power.setName(request.getParameter("name"));
        power.setDescription(request.getParameter("description"));

        powerDao.updatePower(power);
        return "redirect:/powers";
    }

}
