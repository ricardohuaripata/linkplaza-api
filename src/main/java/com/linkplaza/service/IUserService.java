package com.linkplaza.service;

import java.util.UUID;

import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(UUID userId);

    User getUserByEmail(String email);
}
