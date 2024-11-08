package com.linkplaza.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.linkplaza.entity.User;
import com.linkplaza.repository.UserRepository;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("No user found with email '" + username + "'");
        } else {
            return new UserPrincipal(user.get());
        }
    }
}
