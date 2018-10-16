package com.greenfoxacademy.todoappmysql.controllers;

import com.greenfoxacademy.todoappmysql.repositories.AssigneeRepository;
import com.greenfoxacademy.todoappmysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TodoRestController.CONTROLLER_ROOT)
public class TodoRestController {

    public static final String CONTROLLER_ROOT = "/api/todo/";

    TodoRepository todoRepository;
    AssigneeRepository assigneeRepository;

    @Autowired
    public TodoRestController(TodoRepository todoRepository,
                              AssigneeRepository assigneeRepository) {
        this.todoRepository = todoRepository;
        this.assigneeRepository = assigneeRepository;
    }


}
