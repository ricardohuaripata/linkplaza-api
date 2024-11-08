package com.linkplaza.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.ClaimUsernameDto;
import com.linkplaza.dto.UpdateProfileDto;
import com.linkplaza.entity.CustomLink;
import com.linkplaza.entity.SocialLink;
import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.entity.User;
import com.linkplaza.repository.CustomLinkRepository;
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
    @Autowired
    private CustomLinkRepository customLinkRepository;

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
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No user found with username '" + username + "'"));
    }

    @Override
    public User claimUsername(ClaimUsernameDto claimUsernameDto) {
        Optional<User> anyUser = userRepository.findByUsername(claimUsernameDto.getUsername());

        if (anyUser.isPresent()) {
            throw new IllegalArgumentException(
                    "The username '" + claimUsernameDto.getUsername() + "' is already taken.");
        }

        String authEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = getUserByEmail(authEmail);

        user.setUsername(claimUsernameDto.getUsername());
        user.setDateLastModified(new Date());

        return userRepository.save(user);

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
        socialLink.setUrl(addSocialLinkDto.getUrl());
        socialLink.setPosition(0);
        socialLink.setActive(true);

        user.getSocialLinks().add(socialLinkRepository.save(socialLink));
        return userRepository.save(user);
    }

    @Override
    public User addCustomLink(AddCustomLinkDto addCustomLinkDto) {
        String authEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = getUserByEmail(authEmail);

        CustomLink customLink = new CustomLink();
        customLink.setUser(user);
        customLink.setUrl(addCustomLinkDto.getUrl());
        customLink.setTitle(addCustomLinkDto.getTitle());
        customLink.setPosition(0);
        customLink.setActive(true);

        user.getCustomLinks().add(customLinkRepository.save(customLink));
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(UpdateProfileDto updateProfileDto) {
        String authEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = getUserByEmail(authEmail);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(
                context -> context.getSource() != null && ((String) context.getSource()).trim().isEmpty() == false);
        modelMapper.map(updateProfileDto, user);

        user.setDateLastModified(new Date());
        return userRepository.save(user);
    }

}