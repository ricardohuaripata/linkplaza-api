package com.linkplaza.service;

import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(Long userId);

    User getUserByEmail(String email);
}
