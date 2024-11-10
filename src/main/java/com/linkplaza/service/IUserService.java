package com.linkplaza.service;

import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(Long id);

    User getUserByEmail(String email);

    User getAuthenticatedUser();

}
