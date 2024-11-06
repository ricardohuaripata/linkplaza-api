package com.linkplaza.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.common.AppConstants;
import com.linkplaza.dto.SignInDto;
import com.linkplaza.dto.SignUpDto;
import com.linkplaza.entity.User;
import com.linkplaza.enumeration.TokenType;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.security.JwtTokenService;
import com.linkplaza.security.UserPrincipal;
import com.linkplaza.service.impl.AuthServiceImpl;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        User newUser = authService.signUp(signUpDto);
        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("User created successfully.");
        successResponse.setData(newUser);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto signInDto) {

        User user = authService.signIn(signInDto);

        String token = jwtTokenService.generateToken(new UserPrincipal(user), TokenType.AUTH_TOKEN);

        HttpHeaders newHttpHeaders = new HttpHeaders();
        newHttpHeaders.add(AppConstants.TOKEN_HEADER, token);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Successful sign in.");
        successResponse.setData(user);

        return new ResponseEntity<>(successResponse, newHttpHeaders, HttpStatus.OK);
    }
}
