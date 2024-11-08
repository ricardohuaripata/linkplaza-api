package com.linkplaza.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.ClaimUsernameDto;
import com.linkplaza.entity.SocialLink;
import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.entity.User;
import com.linkplaza.exception.UserNotFoundException;
import com.linkplaza.exception.UsernameAlreadyTakenException;
import com.linkplaza.repository.SocialLinkRepository;
import com.linkplaza.repository.UserRepository;
import com.linkplaza.service.ISocialPlatformService;
import com.linkplaza.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ISocialPlatformService socialPlatformService;
    @Autowired
    private SocialLinkRepository socialLinkRepository;

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

    @Override
    public User addSocialLink(AddSocialLinkDto addSocialLinkDto) {
        SocialPlatform socialPlatform = socialPlatformService
                .getSocialPlatformById(addSocialLinkDto.getSocialPlatformId());

        String authEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = getUserByEmail(authEmail);

        // verificar si ya existe un SocialLink con la misma SocialPlatform
        boolean alreadyExists = user.getSocialLinks().stream()
                .anyMatch(link -> link.getSocialPlatform().equals(socialPlatform));

        if (alreadyExists) {
            throw new IllegalArgumentException("You already has a link for this social platform.");
        }

        SocialLink socialLink = new SocialLink();
        socialLink.setUser(user);
        socialLink.setSocialPlatform(socialPlatform);
        socialLink.setLink(addSocialLinkDto.getLink());
        socialLink.setPosition(0);
        socialLink.setActive(true);

        user.getSocialLinks().add(socialLinkRepository.save(socialLink));
        return userRepository.save(user);
    }

}