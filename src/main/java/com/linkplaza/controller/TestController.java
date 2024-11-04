package com.linkplaza.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<?> helloWorld() {
        String message = "Hello world! :)";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
