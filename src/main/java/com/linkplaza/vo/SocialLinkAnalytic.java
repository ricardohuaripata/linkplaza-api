package com.linkplaza.vo;

import com.linkplaza.entity.SocialLink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialLinkAnalytic {
    private SocialLink socialLink;
    private Long clicks;
    private Long uniqueClicks;
}
