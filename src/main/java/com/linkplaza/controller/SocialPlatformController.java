package com.linkplaza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.service.ISocialPlatformService;

@RestController
@RequestMapping("/api/v1/socialPlatform")
public class SocialPlatformController {
    @Autowired
    private ISocialPlatformService socialPlatformService;

    @GetMapping()
    public ResponseEntity<?> getSocialPlatforms() {
        List<SocialPlatform> socialPlatforms = socialPlatformService.getSocialPlatforms();

        SuccessResponse<List<SocialPlatform>> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Showing social platforms.");
        successResponse.setData(socialPlatforms);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
}
