package com.linkplaza.service;

import java.util.List;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.CreatePageDto;
import com.linkplaza.dto.UpdatePageDto;
import com.linkplaza.dto.UpdateSocialLinkDto;
import com.linkplaza.entity.Page;
import com.linkplaza.entity.SocialLink;

public interface IPageService {

    Page getPageById(Long id);

    Page getPageByUrl(String url);

    SocialLink getSocialLinkById(Long id);

    Page createPage(CreatePageDto createPageDto);

    Page updatePage(Long pageId, UpdatePageDto updatePageDto);

    Page addSocialLink(Long pageId, AddSocialLinkDto addSocialLinkDto);

    Page addCustomLink(Long pageId, AddCustomLinkDto addCustomLinkDto);

    Page updateSocialLink(Long socialLinkId, UpdateSocialLinkDto updateSocialLinkDto);

    Page sortSocialLinks(Long pageId, List<Long> ids);

    void deletePage(Long pageId);

}
