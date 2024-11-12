package com.linkplaza.service.impl;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.CreatePageDto;
import com.linkplaza.dto.UpdatePageDto;
import com.linkplaza.entity.CustomLink;
import com.linkplaza.entity.Page;
import com.linkplaza.entity.SocialLink;
import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.entity.User;
import com.linkplaza.repository.CustomLinkRepository;
import com.linkplaza.repository.PageRepository;
import com.linkplaza.repository.SocialLinkRepository;
import com.linkplaza.service.IPageService;
import com.linkplaza.service.ISocialPlatformService;
import com.linkplaza.service.IUserService;

@Service
public class PageServiceImpl implements IPageService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISocialPlatformService socialPlatformService;
    @Autowired
    private SocialLinkRepository socialLinkRepository;
    @Autowired
    private CustomLinkRepository customLinkRepository;

    @Override
    public Page getPageById(Long id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No page found with id " + id));
    }

    @Override
    public Page getPageByUrl(String url) {
        return pageRepository.findByUrl(url)
                .orElseThrow(() -> new EntityNotFoundException("No page found with url '" + url + "'"));
    }

    @Override
    public Page createPage(CreatePageDto createPageDto) {
        User user = userService.getAuthenticatedUser();

        boolean alreadyExists = pageRepository.existsByUrl(createPageDto.getUrl());
        if (alreadyExists) {
            throw new IllegalArgumentException("The URL '" + createPageDto.getUrl() + "' is already taken.");
        }

        Page page = new Page();
        Date currentDate = new Date();

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(
                context -> context.getSource() != null && ((String) context.getSource()).trim().isEmpty() == false);
        modelMapper.map(createPageDto, page);

        page.setUser(user);
        page.setDateCreated(currentDate);
        page.setDateLastModified(currentDate);

        return pageRepository.save(page);
    }

    @Override
    public Page updatePage(Long pageId, UpdatePageDto updatePageDto) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new IllegalArgumentException("Your are not the owner of this page.");
        }

        if (updatePageDto.getUrl() != null && updatePageDto.getUrl().trim().isEmpty() == false) {
            boolean alreadyExists = pageRepository.existsByUrl(updatePageDto.getUrl());
            if (alreadyExists) {
                throw new IllegalArgumentException("The URL '" + updatePageDto.getUrl() + "' is already taken.");
            }
            page.setUrl(updatePageDto.getUrl());
        }
        if (updatePageDto.getTitle() != null && updatePageDto.getTitle().trim().isEmpty() == false) {
            page.setTitle(updatePageDto.getTitle());
        }
        if (updatePageDto.getBio() != null && updatePageDto.getBio().trim().isEmpty() == false) {
            page.setBio(updatePageDto.getBio());
        }
        if (updatePageDto.getPictureUrl() != null && updatePageDto.getPictureUrl().trim().isEmpty() == false) {
            page.setPictureUrl(updatePageDto.getPictureUrl());
        }

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page addSocialLink(Long pageId, AddSocialLinkDto addSocialLinkDto) {
        SocialPlatform socialPlatform = socialPlatformService
                .getSocialPlatformById(addSocialLinkDto.getSocialPlatformId());

        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new IllegalArgumentException("Your are not the owner of this page.");
        }

        // verificar si ya existe un SocialLink con la misma SocialPlatform
        boolean alreadyExists = page.getSocialLinks().stream()
                .anyMatch(link -> link.getSocialPlatform().equals(socialPlatform));

        if (alreadyExists) {
            throw new IllegalArgumentException("The page has already a link for this social platform.");
        }

        SocialLink socialLink = new SocialLink();
        socialLink.setPage(page);
        socialLink.setSocialPlatform(socialPlatform);
        socialLink.setUrl(addSocialLinkDto.getUrl());
        socialLink.setPosition(0);
        socialLink.setActive(true);

        page.getSocialLinks().add(socialLinkRepository.save(socialLink));
        page.setDateLastModified(new Date());
        return page;
    }

    @Override
    public Page addCustomLink(Long pageId, AddCustomLinkDto addCustomLinkDto) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new IllegalArgumentException("Your are not the owner of this page.");
        }

        CustomLink customLink = new CustomLink();
        customLink.setPage(page);
        customLink.setUrl(addCustomLinkDto.getUrl());
        customLink.setTitle(addCustomLinkDto.getTitle());
        customLink.setPosition(0);
        customLink.setActive(true);
        customLinkRepository.save(customLink);

        page.setDateLastModified(new Date());
        return page;
    }

}
