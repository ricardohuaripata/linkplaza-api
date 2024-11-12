package com.linkplaza.service;

import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.CreatePageDto;
import com.linkplaza.dto.UpdatePageDto;
import com.linkplaza.entity.Page;

public interface IPageService {

    Page getPageById(Long id);

    Page getPageByUrl(String url);

    Page createPage(CreatePageDto createPageDto);

    Page updatePage(Long pageId, UpdatePageDto updatePageDto);

    Page addSocialLink(Long pageId, AddSocialLinkDto addSocialLinkDto);

    Page addCustomLink(Long pageId, AddCustomLinkDto addCustomLinkDto);

    void deletePage(Long pageId);

}
