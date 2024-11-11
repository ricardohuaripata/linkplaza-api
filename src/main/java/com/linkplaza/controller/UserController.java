package com.linkplaza.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.dto.AccountVerifyDto;
import com.linkplaza.entity.User;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("User found.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/account/info")
    public ResponseEntity<?> getAuthUserInfo() {
        User user = userService.getAuthenticatedUser();

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Showing user info.");
        successResponse.setData(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/account/verify")
    public ResponseEntity<?> accountVerify(@RequestBody @Valid AccountVerifyDto accountVerifyDto) {
        User user = userService.accountVerify(accountVerifyDto);
        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Account verified successfully.");
        successResponse.setData(user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/account/send-verification-code")
    public ResponseEntity<?> sendAccountVerificationCode() {
        userService.sendAccountVerificationCode();
        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("A temporary verification code has sent to your email.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
