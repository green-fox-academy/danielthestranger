package com.greenfoxacademy.todoappmysql.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping(value = "/")
    public String redirectToTodo() {
        return "redirect:/todo/";
    }
}
