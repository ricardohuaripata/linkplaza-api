package com.linkplaza.service;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.ClaimUsernameDto;
import com.linkplaza.entity.User;

public interface IUserService {
    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User claimUsername(ClaimUsernameDto claimUsernameDto);

    User addSocialLink(AddSocialLinkDto addSocialLinkDto);

    User addCustomLink(AddCustomLinkDto addCustomLinkDto);

}
