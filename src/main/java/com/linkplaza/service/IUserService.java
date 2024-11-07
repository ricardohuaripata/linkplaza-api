package com.linkplaza.service;

import com.linkplaza.dto.ClaimUsernameDto;
import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(Long userId);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User claimUsername(ClaimUsernameDto claimUsernameDto);

}
