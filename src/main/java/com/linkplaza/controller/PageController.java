package com.linkplaza.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.CreatePageDto;
import com.linkplaza.dto.SortLinksDto;
import com.linkplaza.dto.UpdateCustomLinkDto;
import com.linkplaza.dto.UpdatePageDto;
import com.linkplaza.dto.UpdateSocialLinkDto;
import com.linkplaza.entity.Page;
import com.linkplaza.response.PageResponse;
import com.linkplaza.response.SuccessResponse;
import com.linkplaza.service.IPageService;

@RestController
@RequestMapping("/api/v1/page")
public class PageController {
    @Autowired
    private IPageService pageService;

    // PAGE ENDPOINTS
    @GetMapping("/{url}")
    public ResponseEntity<?> getPageByUrl(@PathVariable("url") String url) {
        Page page = pageService.getPageByUrl(url);

        PageResponse pageResponse = new PageResponse();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(page, pageResponse);
        pageResponse.setUserVerified(page.getUser().isEmailVerified());

        SuccessResponse<PageResponse> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Page found.");
        successResponse.setData(pageResponse);

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePage(@PathVariable("id") Long id) {
        pageService.deletePage(id);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Page deleted successfully.");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // SOCIAL LINK ENDPOINTS
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

    @PutMapping("/{id}/social-link/sort")
    public ResponseEntity<?> sortSocialLinks(@PathVariable("id") Long id,
            @RequestBody @Valid SortLinksDto sortLinksDto) {
        Page page = pageService.sortSocialLinks(id, sortLinksDto.getIds());

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Social links sorted successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PatchMapping("/social-link/{id}")
    public ResponseEntity<?> updateSocialLink(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateSocialLinkDto updateSocialLinkDto) {
        Page page = pageService.updateSocialLink(id, updateSocialLinkDto);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Social link updated successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/social-link/{id}")
    public ResponseEntity<?> deleteSocialLink(@PathVariable("id") Long id) {
        Page page = pageService.deleteSocialLink(id);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Social link deleted successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // CUSTOM LINK ENDPOINTS
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

    @PutMapping("/{id}/custom-link/sort")
    public ResponseEntity<?> sortCustomLinks(@PathVariable("id") Long id,
            @RequestBody @Valid SortLinksDto sortLinksDto) {
        Page page = pageService.sortCustomLinks(id, sortLinksDto.getIds());

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Custom links sorted successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PatchMapping("/custom-link/{id}")
    public ResponseEntity<?> updateCustomLink(@PathVariable("id") Long id,
            @RequestBody @Valid UpdateCustomLinkDto updateCustomLinkDto) {
        Page page = pageService.updateCustomLink(id, updateCustomLinkDto);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Custom link updated successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/custom-link/{id}")
    public ResponseEntity<?> deleteCustomLink(@PathVariable("id") Long id) {
        Page page = pageService.deleteCustomLink(id);

        SuccessResponse<Page> successResponse = new SuccessResponse<>();
        successResponse.setStatus("success");
        successResponse.setMessage("Custom link deleted successfully.");
        successResponse.setData(page);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
