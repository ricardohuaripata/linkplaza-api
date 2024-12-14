package com.linkplaza.vo;

import com.linkplaza.entity.CustomLink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomLinkAnalytic {
    private CustomLink customLink;
    private Long clicks;
    private Long uniqueClicks;
}
