package com.linkplaza.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.response.SuccessResponse;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping()
    public ResponseEntity<?> helloWorld() {
        SuccessResponse<?> successResponse = SuccessResponse.builder()
                .status("success")
                .message("Hello world! :)")
                .build();
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
