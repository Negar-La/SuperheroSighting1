//package com.we.SuperHeroSightings.controller;
//
//import com.we.SuperHeroSightings.dao.SightingDao;
//import com.we.SuperHeroSightings.entities.Sighting;
//import com.we.SuperHeroSightings.service.SightingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class MainController {
//    @Autowired
//    SightingService sightingService;
//    @GetMapping("/")
//    public String index(Model model) {
//        List<Sighting> sightings = sightingService.getAllSightings();
//        if (sightings.size() > 10) {
//            sightings = sightings.subList(0, 10);
//        }
//        model.addAttribute("sightings", sightings);
//        return "index";
//    }
//
//
//}

