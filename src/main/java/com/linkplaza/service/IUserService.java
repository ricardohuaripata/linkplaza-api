package com.linkplaza.service;

import com.linkplaza.dto.ChangeEmailDto;
import com.linkplaza.dto.ChangePasswordDto;
import com.linkplaza.dto.VerifyCodeDto;
import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(Long id);

    User getUserByEmail(String email);

    User getAuthenticatedUser();

    void sendAccountVerificationCode();

    void sendDeleteAccountVerificationCode();

    User verifyAccount(VerifyCodeDto verifyCodeDto);

    void deleteAccount(VerifyCodeDto verifyCodeDto);

    String generateVerificationCode(User user, String type);

    User changePassword(ChangePasswordDto changePasswordDto);

    User changeEmail(ChangeEmailDto changeEmailDto);

}
