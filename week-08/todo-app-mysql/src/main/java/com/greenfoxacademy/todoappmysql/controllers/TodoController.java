package com.greenfoxacademy.todoappmysql.controllers;

import com.greenfoxacademy.todoappmysql.models.Todo;
import com.greenfoxacademy.todoappmysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/todo")
public class TodoController {

    TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        todoRepository.save(new Todo("Not urgent done task", false, true));
        todoRepository.save(new Todo("Urgent done task", true, true));
        todoRepository.save(new Todo("Outstanding task", false, false));
        todoRepository.save(new Todo("Outstanding urgent task", true, false));

    }

    @GetMapping(value = {"/", "/list"})
    public String list(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todoslist";
    }
}
