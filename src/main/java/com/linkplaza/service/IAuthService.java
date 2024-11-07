package com.linkplaza.service;

import com.linkplaza.dto.AccountVerifyDto;
import com.linkplaza.dto.SignInDto;
import com.linkplaza.dto.SignUpDto;
import com.linkplaza.entity.User;

public interface IAuthService {
    void signUp(SignUpDto signUpDto);

    User signIn(SignInDto signInDto);

    User completeSignUp(AccountVerifyDto accountVerifyDto);

}
