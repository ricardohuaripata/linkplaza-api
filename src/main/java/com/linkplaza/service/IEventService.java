package com.linkplaza.service;

import java.util.Date;

import com.linkplaza.vo.PageAnalytic;

public interface IEventService {
    void logVisit(Long pageId, String ipAddress);

    PageAnalytic getPageAnalytic(Long pageId, Date startDate, Date endDate);

}
