package com.linkplaza.service;

import com.linkplaza.dto.AccountVerifyDto;
import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(Long id);

    User getUserByEmail(String email);

    User getAuthenticatedUser();

    User accountVerify(AccountVerifyDto accountVerifyDto);

    void sendAccountVerificationCode();

    String generateVerificationCode(User user, String type);

}
