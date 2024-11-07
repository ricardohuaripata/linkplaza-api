package com.linkplaza.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.linkplaza.dto.ClaimUsernameDto;
import com.linkplaza.entity.User;
import com.linkplaza.exception.UserNotFoundException;
import com.linkplaza.exception.UsernameAlreadyTakenException;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User claimUsername(ClaimUsernameDto claimUsernameDto) {
        Optional<User> anyUser = userRepository.findByUsername(claimUsernameDto.getUsername());

        if (anyUser.isPresent()) {
            throw new UsernameAlreadyTakenException();
        } else {
            String authEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            User user = getUserByEmail(authEmail);

            user.setUsername(claimUsernameDto.getUsername());
            user.setDateLastModified(new Date());

            return userRepository.save(user);
        }
    }

}
