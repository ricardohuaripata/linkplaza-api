package com.linkplaza.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.entity.User;
import com.linkplaza.response.SuccessResponse;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<?> helloWorld() {
        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Hello from public endpoint! :)");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/secure")
    public ResponseEntity<?> securedEndpoint() {
        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Hello from secured endpoint! :)");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
