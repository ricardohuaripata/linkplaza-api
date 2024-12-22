package com.linkplaza.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.common.AppConstants;
import com.linkplaza.dto.ForgotPasswordDto;
import com.linkplaza.dto.ResetPasswordDto;
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
        UserPrincipal userPrincipal = new UserPrincipal(user);
        String token = jwtTokenService.generateToken(userPrincipal, TokenType.AUTH_TOKEN);

        // añadir el token de autenticacion a una cookie httpOnly
        String cookieHeader = AppConstants.TOKEN_HEADER + "=" + token + "; HttpOnly; Secure; SameSite=None; Path=/";
        response.addHeader("Set-Cookie", cookieHeader);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Successful sign up.");
        successResponse.setData(user);
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto signInDto, HttpServletResponse response) {
        User user = authService.authenticateUser(signInDto);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        String token = jwtTokenService.generateToken(userPrincipal, TokenType.AUTH_TOKEN);

        // añadir el token de autenticacion a una cookie httpOnly
        String cookieHeader = AppConstants.TOKEN_HEADER + "=" + token + "; HttpOnly; Secure; SameSite=None; Path=/";
        response.addHeader("Set-Cookie", cookieHeader);

        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Successful sign in.");
        successResponse.setData(user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto) {
        authService.forgotPassword(forgotPasswordDto);

        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Please, check your email to reset your password.");
        return new ResponseEntity<>(successResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) {
        authService.resetPassword(resetPasswordDto);

        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Password reset successfully.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
