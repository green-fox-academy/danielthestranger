package com.greenfoxacademy.todoappmysql.controllers;

import com.greenfoxacademy.todoappmysql.models.Todo;
import com.greenfoxacademy.todoappmysql.repositories.AssigneeRepository;
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
@RequestMapping(TodoController.CONTROLLER_ROOT)
public class TodoController {

    public static final String CONTROLLER_ROOT = "/todo/";
//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    TodoRepository todoRepository;
    AssigneeRepository assigneeRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository, AssigneeRepository assigneeRepository) {
        this.todoRepository = todoRepository;
        this.assigneeRepository = assigneeRepository;

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
                       @RequestParam(value = "assigneeId", required = false) Long assigneeId,
                       Model model) {

        model.addAttribute("newTodo", new Todo());
        String todosAttribute = "todos";

        if (assigneeId != null) {
            model.addAttribute(todosAttribute, todoRepository.findAllByAssignee_Id(assigneeId));
            return "todos";
        }

        if (done != null) {
            model.addAttribute(todosAttribute, todoRepository.findAllByTitleContainingAndDone(title, done));
        } else {
            model.addAttribute(todosAttribute, todoRepository.findAllByTitleContaining(title));
        }
        return "todos";
    }

    @PostMapping("/add")
    public String add(HttpServletRequest request,
                          @ModelAttribute Todo newTodo) {

        todoRepository.save(newTodo);
        return "redirect:" + CONTROLLER_ROOT;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(value = "id") Long id) {
        todoRepository.deleteById(id);
        return "redirect:" + CONTROLLER_ROOT;
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable(value = "id") Long id,
                           Model model) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.equals(Optional.empty()))
            return "redirect:" + CONTROLLER_ROOT;

        model.addAttribute("todo", optionalTodo.get());
        model.addAttribute("possibleAssignees", assigneeRepository.findAll());
        return "todoedit";
    }

    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute Todo todo) {
        todoRepository.save(todo);
        return "redirect:" + CONTROLLER_ROOT;
    }
}
