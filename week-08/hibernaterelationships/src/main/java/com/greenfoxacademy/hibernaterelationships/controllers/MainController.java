package com.greenfoxacademy.hibernaterelationships.controllers;

import com.greenfoxacademy.hibernaterelationships.model.Car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("car", new Car("UAZ"));
        return "index";
    }
}
