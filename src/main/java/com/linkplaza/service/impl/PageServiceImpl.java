package com.linkplaza.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import com.linkplaza.common.AppConstants;
import com.linkplaza.dto.AddCustomLinkDto;
import com.linkplaza.dto.AddSocialLinkDto;
import com.linkplaza.dto.CreatePageDto;
import com.linkplaza.dto.UpdateCustomLinkDto;
import com.linkplaza.dto.UpdatePageDto;
import com.linkplaza.dto.UpdateSocialLinkDto;
import com.linkplaza.entity.CustomLink;
import com.linkplaza.entity.Page;
import com.linkplaza.entity.SocialLink;
import com.linkplaza.entity.SocialPlatform;
import com.linkplaza.entity.User;
import com.linkplaza.repository.CustomLinkRepository;
import com.linkplaza.repository.PageRepository;
import com.linkplaza.repository.SocialLinkRepository;
import com.linkplaza.service.ICloudinaryService;
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
    @Autowired
    private ICloudinaryService cloudinaryService;

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
    public SocialLink getSocialLinkById(Long id) {
        return socialLinkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No social link found with id " + id));
    }

    @Override
    public CustomLink getCustomLinkById(Long id) {
        return customLinkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No custom link found with id " + id));
    }

    @Override
    public Page createPage(CreatePageDto createPageDto) {
        User user = userService.getAuthenticatedUser();

        boolean alreadyExists = pageRepository.existsByUrl(createPageDto.getUrl());
        if (alreadyExists) {
            throw new IllegalArgumentException("The URL is already taken.");
        }

        Page page = new Page();
        Date currentDate = new Date();

        if (createPageDto.getTitle() != null && createPageDto.getTitle().trim().isEmpty() == false) {
            page.setTitle(createPageDto.getTitle());
        }
        if (createPageDto.getBio() != null && createPageDto.getBio().trim().isEmpty() == false) {
            page.setBio(createPageDto.getBio());
        }
        if (createPageDto.getBackgroundColor() != null
                && createPageDto.getBackgroundColor().trim().isEmpty() == false) {
            page.setBackgroundColor(createPageDto.getBackgroundColor());
        }
        if (createPageDto.getFontColor() != null && createPageDto.getFontColor().trim().isEmpty() == false) {
            page.setFontColor(createPageDto.getFontColor());
        }
        if (createPageDto.getButtonBackgroundColor() != null
                && createPageDto.getButtonBackgroundColor().trim().isEmpty() == false) {
            page.setButtonBackgroundColor(createPageDto.getButtonBackgroundColor());
        }
        if (createPageDto.getButtonFontColor() != null
                && createPageDto.getButtonFontColor().trim().isEmpty() == false) {
            page.setButtonFontColor(createPageDto.getButtonFontColor());
        }
        if (createPageDto.getButtonRounded() != null) {
            page.setButtonRounded(createPageDto.getButtonRounded());
        }
        if (createPageDto.getFontStyle() != null) {
            page.setFontStyle(createPageDto.getFontStyle());
        }
        if (createPageDto.getBackgroundStyle() != null) {
            page.setBackgroundStyle(createPageDto.getBackgroundStyle());
        }

        page.setUrl(createPageDto.getUrl());
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
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        if (updatePageDto.getUrl() != null) {
            boolean alreadyExists = pageRepository.existsByUrl(updatePageDto.getUrl());
            if (alreadyExists) {
                throw new IllegalArgumentException("The URL is already taken.");
            }
            page.setUrl(updatePageDto.getUrl());
        }
        if (updatePageDto.getTitle() != null && updatePageDto.getTitle().trim().isEmpty() == false) {
            page.setTitle(updatePageDto.getTitle());
        }
        if (updatePageDto.getBio() != null && updatePageDto.getBio().trim().isEmpty() == false) {
            page.setBio(updatePageDto.getBio());
        }
        if (updatePageDto.getBackgroundColor() != null
                && updatePageDto.getBackgroundColor().trim().isEmpty() == false) {
            page.setBackgroundColor(updatePageDto.getBackgroundColor());
        }
        if (updatePageDto.getFontColor() != null && updatePageDto.getFontColor().trim().isEmpty() == false) {
            page.setFontColor(updatePageDto.getFontColor());
        }
        if (updatePageDto.getButtonBackgroundColor() != null
                && updatePageDto.getButtonBackgroundColor().trim().isEmpty() == false) {
            page.setButtonBackgroundColor(updatePageDto.getButtonBackgroundColor());
        }
        if (updatePageDto.getButtonFontColor() != null
                && updatePageDto.getButtonFontColor().trim().isEmpty() == false) {
            page.setButtonFontColor(updatePageDto.getButtonFontColor());
        }
        if (updatePageDto.getButtonRounded() != null) {
            page.setButtonRounded(updatePageDto.getButtonRounded());
        }
        if (updatePageDto.getFontStyle() != null) {
            page.setFontStyle(updatePageDto.getFontStyle());
        }
        if (updatePageDto.getBackgroundStyle() != null) {
            page.setBackgroundStyle(updatePageDto.getBackgroundStyle());
        }

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page uploadPicture(Long pageId, MultipartFile picture) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        List<String> allowedContentTypes = Arrays.asList("image/jpg", "image/jpeg", "image/png", "image/webp");

        String contentType = picture.getContentType();
        if (contentType == null || !allowedContentTypes.contains(contentType)) {
            throw new IllegalArgumentException("Only (jpg, jpeg, png, webp) files are allowed.");
        }

        try {
            // eliminar imagen actual
            if (page.getPictureId() != null) {
                cloudinaryService.delete(page.getPictureId());
            }

            BufferedImage img = ImageIO.read(picture.getInputStream());
            int width = img.getWidth();

            String transformation = (width >= 400) ? "c_fill,w_400,h_400" : "c_fill,w_" + width + ",h_" + width;

            // parametros para la optimizacion de la imagen
            Map<String, Object> uploadParams = ObjectUtils.asMap("quality", "auto", "fetch_format", "auto", "transformation", transformation);

            Map uploadResult = cloudinaryService.upload(picture, uploadParams);
            String pictureUrl = (String) uploadResult.get("url");
            String pictureId = (String) uploadResult.get("public_id");

            page.setPictureUrl(pictureUrl);
            page.setPictureId(pictureId);
            page.setDateLastModified(new Date());
            return pageRepository.save(page);

        } catch (IOException e) {
            throw new RuntimeException("Error while uploading picture.");
        }

    }

    @Override
    public Page addSocialLink(Long pageId, AddSocialLinkDto addSocialLinkDto) {
        SocialPlatform socialPlatform = socialPlatformService
                .getSocialPlatformById(addSocialLinkDto.getSocialPlatformId());

        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
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
        socialLink.setDateCreated(new Date());

        page.getSocialLinks().add(socialLinkRepository.save(socialLink));
        page.getSocialLinks().sort(Comparator.comparingInt(SocialLink::getPosition)
                .thenComparing(Comparator.comparing(SocialLink::getDateCreated).reversed()));
        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page addCustomLink(Long pageId, AddCustomLinkDto addCustomLinkDto) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        CustomLink customLink = new CustomLink();
        customLink.setPage(page);
        customLink.setUrl(addCustomLinkDto.getUrl());
        customLink.setTitle(addCustomLinkDto.getTitle());
        customLink.setPosition(0);
        customLink.setActive(true);
        customLink.setDateCreated(new Date());
        customLinkRepository.save(customLink);

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page updateSocialLink(Long socialLinkId, UpdateSocialLinkDto updateSocialLinkDto) {
        SocialLink socialLink = getSocialLinkById(socialLinkId);
        User user = userService.getAuthenticatedUser();
        Page page = socialLink.getPage();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        if (updateSocialLinkDto.getUrl() != null && updateSocialLinkDto.getUrl().trim().isEmpty() == false) {
            socialLink.setUrl(updateSocialLinkDto.getUrl());
        }
        if (updateSocialLinkDto.getActive() != null) {
            socialLink.setActive(updateSocialLinkDto.getActive());
        }

        socialLinkRepository.save(socialLink);

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page updateCustomLink(Long customLinkId, UpdateCustomLinkDto updateCustomLinkDto) {
        CustomLink customLink = getCustomLinkById(customLinkId);
        User user = userService.getAuthenticatedUser();
        Page page = customLink.getPage();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        if (updateCustomLinkDto.getUrl() != null && updateCustomLinkDto.getUrl().trim().isEmpty() == false) {
            customLink.setUrl(updateCustomLinkDto.getUrl());
        }
        if (updateCustomLinkDto.getTitle() != null && updateCustomLinkDto.getTitle().trim().isEmpty() == false) {
            customLink.setTitle(updateCustomLinkDto.getTitle());
        }
        if (updateCustomLinkDto.getActive() != null) {
            customLink.setActive(updateCustomLinkDto.getActive());
        }

        customLinkRepository.save(customLink);

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page sortSocialLinks(Long pageId, List<Long> ids) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        // obtener los SocialLinks existentes
        List<SocialLink> socialLinks = page.getSocialLinks();

        // validar mismo tamaño de lista
        if (socialLinks.size() != ids.size()) {
            throw new IllegalArgumentException("Invalid list size.");
        }

        // validar que todos los IDs proporcionados existan y pertenezcan a la página
        List<Long> existingIds = socialLinks.stream().map(SocialLink::getId).collect(Collectors.toList());
        for (Long id : ids) {
            if (!existingIds.contains(id)) {
                throw new IllegalArgumentException("Invalid social link id: " + id);
            }
        }

        // validar ids no repetidos
        List<Long> uniqueIds = ids.stream().distinct().collect(Collectors.toList());
        if (uniqueIds.size() != ids.size()) {
            throw new IllegalArgumentException("Duplicate ids.");
        }

        for (int i = 0; i < ids.size(); i++) {
            for (SocialLink socialLink : socialLinks) {
                if (ids.get(i).equals(socialLink.getId())) {
                    socialLink.setPosition(i);
                    break;
                }
            }

        }
        // guardar nuevas posiciones
        socialLinkRepository.saveAll(socialLinks);
        page.setDateLastModified(new Date());

        // ordenar la lista para la respuesta
        socialLinks.sort(Comparator.comparingInt(SocialLink::getPosition));

        return pageRepository.save(page);
    }

    @Override
    public Page sortCustomLinks(Long pageId, List<Long> ids) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        // obtener los CustomLinks existentes
        List<CustomLink> customLinks = page.getCustomLinks();

        // validar mismo tamaño de lista
        if (customLinks.size() != ids.size()) {
            throw new IllegalArgumentException("Invalid list size.");
        }

        // validar que todos los IDs proporcionados existan y pertenezcan a la página
        List<Long> existingIds = customLinks.stream().map(CustomLink::getId).collect(Collectors.toList());
        for (Long id : ids) {
            if (!existingIds.contains(id)) {
                throw new IllegalArgumentException("Invalid custom link id: " + id);
            }
        }

        // validar ids no repetidos
        List<Long> uniqueIds = ids.stream().distinct().collect(Collectors.toList());
        if (uniqueIds.size() != ids.size()) {
            throw new IllegalArgumentException("Duplicate ids.");
        }

        for (int i = 0; i < ids.size(); i++) {
            for (CustomLink customLink : customLinks) {
                if (ids.get(i).equals(customLink.getId())) {
                    customLink.setPosition(i);
                    break;
                }
            }

        }
        // guardar nuevas posiciones
        customLinkRepository.saveAll(customLinks);
        page.setDateLastModified(new Date());

        // ordenar la lista para la respuesta
        customLinks.sort(Comparator.comparingInt(CustomLink::getPosition));

        return pageRepository.save(page);
    }

    @Override
    public User deletePage(Long pageId) {
        Page page = getPageById(pageId);
        User user = userService.getAuthenticatedUser();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        pageRepository.delete(page);
        return user;
    }

    @Override
    public Page deleteSocialLink(Long socialLinkId) {
        SocialLink socialLink = getSocialLinkById(socialLinkId);
        User user = userService.getAuthenticatedUser();
        Page page = socialLink.getPage();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        socialLinkRepository.delete(socialLink);

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

    @Override
    public Page deleteCustomLink(Long customLinkId) {
        CustomLink customLink = getCustomLinkById(customLinkId);
        User user = userService.getAuthenticatedUser();
        Page page = customLink.getPage();

        if (!page.getUser().equals(user)) {
            throw new AccessDeniedException(AppConstants.NOT_PAGE_OWNER);
        }

        customLinkRepository.delete(customLink);

        page.setDateLastModified(new Date());
        return pageRepository.save(page);
    }

}
