package com.linkplaza.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageAnalytic {
    private Long totalViews;
    private Long totalUniqueViews;
    private Long totalSocialLinkClicks;
    private Long totalSocialLinkUniqueClicks;
    private Long totalCustomLinkClicks;
    private Long totalCustomLinkUniqueClicks;
    private List<Timeserie> timeseries;
    private List<CustomLinkAnalytic> customLinkAnalytics;
    private List<SocialLinkAnalytic> socialLinkAnalytics;

}
