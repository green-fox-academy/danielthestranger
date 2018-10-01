package com.greenfoxacademy.restbackend.controller;

import com.greenfoxacademy.restbackend.service.ArrayActionService;
import com.greenfoxacademy.restbackend.service.DoUntilService;
import com.greenfoxacademy.restbackend.model.dto.*;
import com.greenfoxacademy.restbackend.model.dto.Error;
import com.greenfoxacademy.restbackend.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class MainRestController {

    private DoUntilService doUntilService;
    private ArrayActionService arrayActionService;
    private LogService logService;

    @Autowired
    public MainRestController(DoUntilService doUntilService, ArrayActionService arrayActionService, LogService logService) {
        this.doUntilService = doUntilService;
        this.arrayActionService = arrayActionService;
        this.logService = logService;
    }

    @GetMapping("/doubling")
    public ResponseEntity<?> doubling(HttpServletRequest request,
                                      @RequestParam(value = "input", required = false) Double received) {
        logService.log(request);

        if (received == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide an input!"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ReceivedAndResult(received, received*2));
    }

    @GetMapping("/greeter")
    public ResponseEntity<?> greeter(HttpServletRequest request,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "title", required = false) String title) {
        logService.log(request);

        if (name == null || name.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide a name!"));
        } else if (title == null || title.isEmpty()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide a title!"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Greeting(name, title));
    }

    @GetMapping("/appenda/{appendable}")
    public ResponseEntity<?> appenda(HttpServletRequest request,
                                     @PathVariable("appendable") String appendable) {
        logService.log(request);

        return ResponseEntity.status(HttpStatus.OK).body(new Appended(appendable));
    }

    @PostMapping("/dountil/{action}")
    public ResponseEntity<?> doUntil(HttpServletRequest request,
                                     @PathVariable("action") String action,
                                     @RequestBody(required = false) Until until) {
        logService.log(request, until);

        if (until == null || until.getUntil() == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide a number!"));
        }

        try {
            return ResponseEntity.status(HttpStatus.OK).body(doUntilService.doActionUntil(action, until.getUntil()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Unsupported action"));
        }
    }

    @PostMapping("/arrays")
    public ResponseEntity<?> handleArray(HttpServletRequest request,
                                        @RequestBody(required = false) ArrayWithAction arrayWithAction) {
        logService.log(request, arrayWithAction);

        if (arrayWithAction == null
                || arrayWithAction.getWhat() == null
                || arrayWithAction.getWhat().isEmpty()
                || arrayWithAction.getNumbers() == null
                || arrayWithAction.getNumbers().size() == 0) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error("Please provide what to do with the numbers!"));
        }

        try {
            String what = arrayWithAction.getWhat();
            List<Integer> numbers = arrayWithAction.getNumbers();
            return ResponseEntity.status(HttpStatus.OK).body(arrayActionService.doArrayAction(what, numbers));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Error(ex.getMessage()));
        }
    }

    @GetMapping("/log")
    public ResponseEntity<?> getLogsWithCount(HttpServletRequest request) {
        logService.log(request);

        return ResponseEntity.status(HttpStatus.OK).body(logService.findAllWithCount());
    }

}
