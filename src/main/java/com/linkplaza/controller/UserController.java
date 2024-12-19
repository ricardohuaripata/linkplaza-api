package com.linkplaza.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.common.AppConstants;
import com.linkplaza.dto.ChangePasswordDto;
import com.linkplaza.dto.VerifyCodeDto;
import com.linkplaza.entity.User;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
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
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PatchMapping("/account/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        userService.changePassword(changePasswordDto);
        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Password changed successfully.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/account/signout")
    public ResponseEntity<?> signOut(HttpServletResponse response) {
        // eliminar la cookie del token
        Cookie cookie = new Cookie(AppConstants.TOKEN_HEADER, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // establece la cookie para que expire inmediatamente
        response.addCookie(cookie); // añadir la cookie a la respuesta

        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Successfully signed out.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/account/verify")
    public ResponseEntity<?> verifyAccount(@RequestBody @Valid VerifyCodeDto verifyCodeDto) {
        User user = userService.verifyAccount(verifyCodeDto);
        SuccessResponse<User> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Account verified successfully.");
        successResponse.setData(user);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/account")
    public ResponseEntity<?> deleteAccount(HttpServletResponse response,
            @RequestBody @Valid VerifyCodeDto verifyCodeDto) {

        userService.deleteAccount(verifyCodeDto);

        Cookie cookie = new Cookie(AppConstants.TOKEN_HEADER, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Account deleted successfully.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/account/send-account-verification-code")
    public ResponseEntity<?> sendAccountVerificationCode() {
        userService.sendAccountVerificationCode();
        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("A temporary verification code has sent to your email.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/account/send-delete-account-verification-code")
    public ResponseEntity<?> sendDeleteAccountVerificationCode() {
        userService.sendDeleteAccountVerificationCode();
        SuccessResponse<?> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("A temporary verification code has sent to your email.");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
