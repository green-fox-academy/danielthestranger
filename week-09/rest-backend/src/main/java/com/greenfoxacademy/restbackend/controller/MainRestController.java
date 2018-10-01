package com.greenfoxacademy.restbackend.controller;

import com.greenfoxacademy.restbackend.model.dto.ReceivedAndResult;
import com.greenfoxacademy.restbackend.model.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
