package com.linkplaza.service;

import com.linkplaza.dto.ForgotPasswordDto;
import com.linkplaza.dto.ResetPasswordDto;
import com.linkplaza.dto.SignInDto;
import com.linkplaza.dto.SignUpDto;
import com.linkplaza.entity.User;

public interface IAuthService {
    User signUp(SignUpDto signUpDto);

    User authenticateUser(SignInDto signInDto);

    void forgotPassword(ForgotPasswordDto forgotPasswordDto);

    void resetPassword(ResetPasswordDto resetPasswordDto);

}
