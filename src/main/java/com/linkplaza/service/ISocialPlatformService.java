package com.linkplaza.service;

import java.util.List;

import com.linkplaza.entity.SocialPlatform;

public interface ISocialPlatformService {

    SocialPlatform getSocialPlatformById(Long id);

    List<SocialPlatform> getSocialPlatforms();

}
