package com.linkplaza.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
import com.linkplaza.service.IAuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto signUpDto, HttpServletResponse response) {
        User user = authService.signUp(signUpDto);
        String token = jwtTokenService.generateToken(new UserPrincipal(user), TokenType.AUTH_TOKEN);

        // añadir el token a una cookie
        Cookie cookie = new Cookie(AppConstants.TOKEN_HEADER, token);
        cookie.setHttpOnly(true); // para que la cookie no sea accesible desde JavaScript
        // cookie.setSecure(true); // para que la cookie solo se envie a traves de https
        cookie.setPath("/");
        response.addCookie(cookie); // añadir la cookie a la respuesta

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Please, check your email to verify your account.");
        successResponse.setData(user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto signInDto, HttpServletResponse response) {
        User user = authService.authenticateUser(signInDto);
        String token = jwtTokenService.generateToken(new UserPrincipal(user), TokenType.AUTH_TOKEN);

        Cookie cookie = new Cookie(AppConstants.TOKEN_HEADER, token);
        cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Successful sign in.");
        successResponse.setData(user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
