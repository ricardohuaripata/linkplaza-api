package com.linkplaza.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.repository.SocialPlatformRepository;
import com.linkplaza.service.ISocialPlatformService;

@Service
public class SocialPlatformServiceImpl implements ISocialPlatformService {
    @Autowired
    private SocialPlatformRepository socialPlatformRepository;

    @Override
    public SocialPlatform getSocialPlatformById(Long id) {
        return socialPlatformRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No social platform found with id " + id));
    }

    @Override
    public List<SocialPlatform> getSocialPlatforms() {
        return socialPlatformRepository.findAll();
    }

}
