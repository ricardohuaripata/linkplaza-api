package com.linkplaza.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.CreatePageDto;
import com.linkplaza.dto.UpdatePageDto;
import com.linkplaza.entity.Page;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.service.IPageService;

@RestController
@RequestMapping("/api/v1/page")
public class PageController {
    @Autowired
    private IPageService pageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPageById(@PathVariable("id") Long id) {
        Page page = pageService.getPageById(id);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Page found.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{url}")
    public ResponseEntity<?> getPageByUrl(@PathVariable("url") String url) {
        Page page = pageService.getPageByUrl(url);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Page found.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createPage(@RequestBody @Valid CreatePageDto createPageDto) {
        Page page = pageService.createPage(createPageDto);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Page created successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePage(@PathVariable("id") Long id, @RequestBody @Valid UpdatePageDto updatePageDto) {
        Page page = pageService.updatePage(id, updatePageDto);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Page updated successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/social-link")
    public ResponseEntity<?> addSocialLink(@PathVariable("id") Long id,
            @RequestBody @Valid AddSocialLinkDto addSocialLinkDto) {
        Page page = pageService.addSocialLink(id, addSocialLinkDto);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Social link added successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/custom-link")
    public ResponseEntity<?> addCustomLink(@PathVariable("id") Long id,
            @RequestBody @Valid AddCustomLinkDto addCustomLinkDto) {
        Page page = pageService.addCustomLink(id, addCustomLinkDto);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Custom link added successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
