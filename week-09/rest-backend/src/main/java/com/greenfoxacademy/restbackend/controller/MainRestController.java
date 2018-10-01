package com.greenfoxacademy.restbackend.controller;

import com.greenfoxacademy.restbackend.model.dto.Appended;
import com.greenfoxacademy.restbackend.model.dto.Greeting;
import com.greenfoxacademy.restbackend.model.dto.ReceivedAndResult;
import com.greenfoxacademy.restbackend.model.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class MainRestController {

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
}
