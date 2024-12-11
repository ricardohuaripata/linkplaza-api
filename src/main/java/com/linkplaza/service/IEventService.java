package com.linkplaza.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.linkplaza.entity.Visit;
import com.linkplaza.vo.PageAnalytic;
import com.linkplaza.vo.VisitCount;

public interface IEventService {
    void logVisit(Long pageId, String ipAddress);

    PageAnalytic getPageAnalytic(Long pageId, Date startDate, Date endDate);

}
