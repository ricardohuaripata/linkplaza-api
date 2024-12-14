package com.linkplaza.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkplaza.entity.CustomLink;
import com.linkplaza.entity.CustomLinkClick;
import com.linkplaza.entity.Page;
import com.linkplaza.entity.Visit;
import com.linkplaza.repository.CustomLinkClickRepository;
import com.linkplaza.repository.VisitRepository;
import com.linkplaza.service.IAnalyticService;
import com.linkplaza.service.IPageService;
import com.linkplaza.vo.CustomLinkAnalytic;
import com.linkplaza.vo.PageAnalytic;
import com.linkplaza.vo.Timeserie;

@Service
public class AnalyticServiceImpl implements IAnalyticService {
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private CustomLinkClickRepository customLinkClickRepository;
    @Autowired
    private IPageService pageService;

    @Override
    public void logVisit(Long pageId, String ipAddress) {
        Page page = pageService.getPageById(pageId);

        Visit visit = new Visit();
        visit.setPage(page);
        visit.setIpAddress(ipAddress);
        visit.setDateCreated(new Date());
        visitRepository.save(visit);
    }

    @Override
    public void logCustomLinkClick(Long customLinkId, String ipAddress) {
        CustomLink customLink = pageService.getCustomLinkById(customLinkId);

        CustomLinkClick customLinkClick = new CustomLinkClick();
        customLinkClick.setCustomLink(customLink);
        customLinkClick.setIpAddress(ipAddress);
        customLinkClick.setDateCreated(new Date());
        customLinkClickRepository.save(customLinkClick);
    }

    @Override
    public PageAnalytic getPageAnalytic(Long pageId, Date startDate, Date endDate) {
        Page page = pageService.getPageById(pageId);

        List<Timeserie> timeseries = new ArrayList<>();

        // inicializar el calendario para recorrer las fechas de inicio a fin
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // recorrer cada dia entre startDate y endDate
        while (!calendar.getTime().after(endDate)) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
            Timeserie timeserie = new Timeserie();
            timeserie.setViews(0L);
            timeserie.setUniqueViews(0L);
            timeserie.setClicks(0L);
            timeserie.setUniqueClicks(0L);
            timeserie.setDate(date);
            timeseries.add(timeserie);

            calendar.add(Calendar.DAY_OF_MONTH, 1); // avanza un dia
        }

        // obtener los registros de visitas del rango de fechas
        List<Visit> visits = visitRepository.findByPageAndDateCreatedBetween(page, startDate, endDate);
        // crear un mapa para rastrear las ip unicas de visitas por fecha
        Map<String, Set<String>> uniqueIpViewsMap = new HashMap<>();

        for (Visit visit : visits) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(visit.getDateCreated());
            uniqueIpViewsMap.putIfAbsent(date, new HashSet<>());

            for (Timeserie timeserie : timeseries) {
                if (timeserie.getDate().equals(date)) {
                    timeserie.setViews(timeserie.getViews() + 1);
                    uniqueIpViewsMap.get(date).add(visit.getIpAddress());
                    break;
                }
            }

        }

        // obtener los registros de clicks del rango de fechas
        List<CustomLinkClick> customLinkClicks = customLinkClickRepository
                .findByCustomLink_PageAndDateCreatedBetween(page, startDate, endDate);
        // crear un mapa para rastrear las ip unicas de clicks por fecha
        Map<String, Set<String>> uniqueIpCustomLinkClicksMap = new HashMap<>();

        for (CustomLinkClick customLinkClick : customLinkClicks) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(customLinkClick.getDateCreated());
            uniqueIpCustomLinkClicksMap.putIfAbsent(date, new HashSet<>());

            for (Timeserie timeserie : timeseries) {
                if (timeserie.getDate().equals(date)) {
                    timeserie.setClicks(timeserie.getClicks() + 1);
                    uniqueIpCustomLinkClicksMap.get(date).add(customLinkClick.getIpAddress());
                    break;
                }
            }

        }
        // setear conteo de ips unicas para views y clicks por fecha
        for (Timeserie timeserie : timeseries) {
            Set<String> uniqueIpViews = uniqueIpViewsMap.get(timeserie.getDate());
            Set<String> uniqueIpCustomLinkClicks = uniqueIpCustomLinkClicksMap.get(timeserie.getDate());
            if (uniqueIpViews != null) {
                timeserie.setUniqueViews((long) uniqueIpViews.size());
            }
            if (uniqueIpCustomLinkClicks != null) {
                timeserie.setUniqueClicks((long) uniqueIpCustomLinkClicks.size());
            }
        }
        // conteo de clicks y uniqueClicks para cada customLink del page
        List<CustomLinkAnalytic> customLinkAnalytics = new ArrayList<>();

        for (CustomLink customLink : page.getCustomLinks()) {
            Long clicks = customLinkClickRepository.countByCustomLink(customLink);
            Long uniqueClicks = customLinkClickRepository.countDistinctByIpAddressAndCustomLink(customLink);

            CustomLinkAnalytic customLinkAnalytic = new CustomLinkAnalytic();
            customLinkAnalytic.setCustomLink(customLink);
            customLinkAnalytic.setClicks(clicks);
            customLinkAnalytic.setUniqueClicks(uniqueClicks);
            customLinkAnalytics.add(customLinkAnalytic);

        }
        // conteo total de eventos relacionados al page
        Long totalViews = visitRepository.countByPage(page);
        Long totalUniqueViews = visitRepository.countDistinctByIpAddressAndPage(page);
        Long totalClicks = customLinkClickRepository.countByCustomLink_Page(page);
        Long totalUniqueClicks = customLinkClickRepository.countDistinctByIpAddressAndCustomLinkPage(page);

        PageAnalytic pageAnalytic = new PageAnalytic();
        pageAnalytic.setTotalViews(totalViews);
        pageAnalytic.setTotalUniqueViews(totalUniqueViews);
        pageAnalytic.setTotalClicks(totalClicks);
        pageAnalytic.setTotalUniqueClicks(totalUniqueClicks);
        pageAnalytic.setTimeseries(timeseries);
        pageAnalytic.setCustomLinkAnalytics(customLinkAnalytics);

        return pageAnalytic;

    }

}
