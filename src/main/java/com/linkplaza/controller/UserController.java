package com.linkplaza.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.ClaimUsernameDto;
import com.linkplaza.dto.UpdateProfileDto;
import com.linkplaza.entity.User;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("User found.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/username")
    public ResponseEntity<?> claimUsername(@RequestBody @Valid ClaimUsernameDto claimUsernameDto) {
        User user = userService.claimUsername(claimUsernameDto);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Successful username claim.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/social-link")
    public ResponseEntity<?> addSocialLink(@RequestBody @Valid AddSocialLinkDto addSocialLinkDto) {
        User user = userService.addSocialLink(addSocialLinkDto);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Social link added successfully.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/custom-link")
    public ResponseEntity<?> addCustomLink(@RequestBody @Valid AddCustomLinkDto addCustomLinkDto) {
        User user = userService.addCustomLink(addCustomLinkDto);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Custom link added successfully.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody @Valid UpdateProfileDto updateProfileDto) {
        User user = userService.updateProfile(updateProfileDto);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Profile updated successfully.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
