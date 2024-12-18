package com.linkplaza.service;

import java.util.Date;

import com.linkplaza.vo.PageAnalytic;

public interface IAnalyticService {
    void logVisit(Long pageId, String ipAddress);

    void logSocialLinkClick(Long socialLinkId, String ipAddress);

    void logCustomLinkClick(Long customLinkId, String ipAddress);

    PageAnalytic getPageAnalytic(Long pageId, Date startDate, Date endDate);

}
