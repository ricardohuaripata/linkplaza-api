package com.linkplaza.response;

import java.util.Date;
import java.util.List;

import com.linkplaza.entity.CustomLink;
import com.linkplaza.entity.SocialLink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
    private Long id;
    private boolean userVerified;
    private String url;
    private String title;
    private String bio;
    private String pictureUrl;
    private String backgroundColor;
    private String fontColor;
    private String buttonBackgroundColor;
    private String buttonFontColor;
    private boolean buttonRounded;
    private Date dateCreated;
    private Date dateLastModified;
    private List<SocialLink> socialLinks;
    private List<CustomLink> customLinks;

}
