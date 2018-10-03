package com.greenfoxacademy.todoappmysql.controllers;

import com.greenfoxacademy.todoappmysql.models.Assignee;
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
@RequestMapping(AssigneeController.CONTROLLER_ROOT)
public class AssigneeController {

    public static final String CONTROLLER_ROOT = "/assignee/";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    AssigneeRepository assigneeRepository;

    @Autowired
    public AssigneeController(AssigneeRepository assigneeRepository) {
        this.assigneeRepository = assigneeRepository;

        // Dummies for testing
        assigneeRepository.save(new Assignee("Human", "human@humans.org"));
    }


    @GetMapping(value = {"/", "/list"})
    public String list(Model model) {
        model.addAttribute("assignees", assigneeRepository.findAll());
        model.addAttribute("newAssignee", new Assignee());
        return "assignees";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Assignee newAssignee) {
        assigneeRepository.save(newAssignee);
        return "redirect:" + CONTROLLER_ROOT;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(value = "id") Long id) {
        assigneeRepository.deleteById(id);
        return "redirect:" + CONTROLLER_ROOT;
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable(value = "id") Long id,
                           Model model) {
        Optional<Assignee> optionalAssignee = assigneeRepository.findById(id);
        if (optionalAssignee.equals(Optional.empty()))
            return "redirect:" + CONTROLLER_ROOT;

        model.addAttribute("assignee", optionalAssignee.get());
        return "assigneeedit";
    }

    @PostMapping("/{id}/edit")
    public String update(@ModelAttribute Assignee assignee) {
        assigneeRepository.save(assignee);
        return "redirect:" + CONTROLLER_ROOT;
    }
}
