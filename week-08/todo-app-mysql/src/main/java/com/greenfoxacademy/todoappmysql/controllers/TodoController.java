package com.greenfoxacademy.todoappmysql.controllers;

import com.greenfoxacademy.todoappmysql.models.Todo;
import com.greenfoxacademy.todoappmysql.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;

        // Dummy todos for testing
        todoRepository.save(new Todo("Not urgent done task", false, true));
        todoRepository.save(new Todo("Urgent done task", true, true));
        todoRepository.save(new Todo("Outstanding task", false, false));
        todoRepository.save(new Todo("Outstanding urgent task", true, false));
    }

    @GetMapping(value = {"/", "/list"})
    public String list(HttpServletRequest request,
                       @RequestParam(value = "done", required = false) Boolean done,
                       @RequestParam(value = "title", defaultValue = "") String title,
                       Model model) {

        if (done != null) {
            model.addAttribute("todos", todoRepository.findAllByTitleContainingAndDone(title, done));
        } else {
            model.addAttribute("todos", todoRepository.findAllByTitleContaining(title));
        }

        model.addAttribute("newTodo", new Todo());

        return "todoslist";
    }

    @PostMapping("/add")
    public String addTodo(HttpServletRequest request,
                          @ModelAttribute Todo newTodo) {

        todoRepository.save(newTodo);
        return "redirect:list";
    }

    @PostMapping("{id}/delete")
    public String deleteTodo(@PathVariable(value = "id") Long id) {
        todoRepository.deleteById(id);
        return "redirect:/todo/";
    }

    @GetMapping("{id}/edit")
    public String showEditTodo(@PathVariable(value = "id") Long id,
                           Model model) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.equals(Optional.empty()))
            return "redirect:/todo/";

        model.addAttribute("todo", optionalTodo.get());
        return "todoedit";
    }

    @PostMapping("{id}/edit")
    public String editTodo(@ModelAttribute Todo todo) {
        todoRepository.save(todo);
        return "redirect:/todo/";
    }
}
