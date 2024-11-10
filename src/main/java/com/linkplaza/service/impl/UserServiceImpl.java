package com.linkplaza.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.linkplaza.entity.User;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No user found with id " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No user found with email '" + email + "'"));
    }

    @Override
    public User getAuthenticatedUser() {
        String authUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return getUserByEmail(authUserEmail);
    }

}