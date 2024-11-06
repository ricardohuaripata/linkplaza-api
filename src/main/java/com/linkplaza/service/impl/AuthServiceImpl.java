package com.linkplaza.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.linkplaza.dto.SignInDto;
import com.linkplaza.dto.SignUpDto;
import com.linkplaza.entity.User;
import com.linkplaza.enumeration.Role;
import com.linkplaza.exception.EmailAlreadyTakenException;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.service.IAuthService;
import com.linkplaza.service.IUserService;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserService userService;

    @Override
    public User signUp(SignUpDto signUpDto) {

        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if (user.isPresent()) {
            throw new EmailAlreadyTakenException();
        } else {
            User newUser = new User();
            Date currentDate = new Date();

            newUser.setEmail(signUpDto.getEmail());
            newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            newUser.setUsername(signUpDto.getUsername());
            newUser.setEmailVerified(false);
            newUser.setDateCreated(currentDate);
            newUser.setDateLastModified(currentDate);
            newUser.setRole(Role.ROLE_USER.name());

            return userRepository.save(newUser);
        }
    }

    @Override
    public User signIn(SignInDto signInDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getEmail(), signInDto.getPassword()));
        return userService.getUserByEmail(signInDto.getEmail());
    }
}
