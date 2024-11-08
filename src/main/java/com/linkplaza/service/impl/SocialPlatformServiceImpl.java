package com.linkplaza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.exception.SocialPlatformNotFoundException;
import com.linkplaza.repository.SocialPlatformRepository;
import com.linkplaza.service.ISocialPlatformService;

@Service
public class SocialPlatformServiceImpl implements ISocialPlatformService {
    @Autowired
    private SocialPlatformRepository socialPlatformRepository;

    @Override
    public SocialPlatform getSocialPlatformById(Long socialPlatformId) {
        return socialPlatformRepository.findById(socialPlatformId).orElseThrow(SocialPlatformNotFoundException::new);
    }

}
