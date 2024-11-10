package com.linkplaza.service;

import com.linkplaza.dto.AccountVerifyDto;
import com.linkplaza.dto.SignInDto;
import com.linkplaza.dto.SignUpDto;
import com.linkplaza.entity.User;

public interface IAuthService {
    User signUp(SignUpDto signUpDto);

    User authenticateUser(SignInDto signInDto);

    User accountVerify(AccountVerifyDto accountVerifyDto);

}
