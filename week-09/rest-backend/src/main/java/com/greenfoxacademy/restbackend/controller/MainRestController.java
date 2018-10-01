package com.greenfoxacademy.restbackend.controller;

import com.greenfoxacademy.restbackend.service.DoUntilService;
import com.greenfoxacademy.restbackend.model.dto.*;
import com.greenfoxacademy.restbackend.model.dto.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MainRestController {

    private DoUntilService doUntilService;

    @Autowired
    public MainRestController(DoUntilService doUntilService) {
        this.doUntilService = doUntilService;
    }

    @GetMapping("/doubling")
    public ResponseEntity<?> doubling(@RequestParam(value = "input", required = false) Double received) {
        if (received == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide an input!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ReceivedAndResult(received, received*2));
        }
    }

    @GetMapping("/greeter")
    public ResponseEntity<?> greeter(@RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "title", required = false) String title) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide a name!"));
        } else if (title == null || title.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide a title!"));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new Greeting(name, title));
        }
    }

    @GetMapping("/appenda/{appendable}")
    public ResponseEntity<?> appenda(@PathVariable("appendable") String appendable) {
        return ResponseEntity.status(HttpStatus.OK).body(new Appended(appendable));
    }

    @PostMapping("/dountil/{action}")
    public ResponseEntity<?> doUntil(@PathVariable("action") String action,
                                     @RequestBody(required = false) Until until) {
        if (until == null || until.getUntil() == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide a number!"));
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(doUntilService.doActionUntil(action, until.getUntil()));
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Unsupported action"));
            }
        }
    }

    @PostMapping("/arrays")
    public ResponseEntity<?> handleArray(@RequestBody(required = false) ArrayWithAction arrayWithAction) {
        if (arrayWithAction == null
                || arrayWithAction.getWhat() == null
                || arrayWithAction.getWhat().isEmpty()
                || arrayWithAction.getNumbers() == null
                || arrayWithAction.getNumbers().size() == 0) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide what to do with the numbers!"));
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body("");
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Unsupported action"));
            }
        }
    }
}
