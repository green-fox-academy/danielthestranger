package com.greenfoxacademy.todoappmysql.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping(value = {"/", "/todo"})
    public String redirectToTodo() {
        return "redirect:" + TodoController.CONTROLLER_ROOT;
    }

    @GetMapping(value = "/assignee")
    public String redirectToAssignee() {
        return "redirect:" + AssigneeController.CONTROLLER_ROOT;
    }
}
