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
import com.linkplaza.entity.SocialLink;
import com.linkplaza.entity.SocialLinkClick;
import com.linkplaza.entity.Visit;
import com.linkplaza.repository.CustomLinkClickRepository;
import com.linkplaza.repository.SocialLinkClickRepository;
import com.linkplaza.repository.VisitRepository;
import com.linkplaza.service.IAnalyticService;
import com.linkplaza.service.IPageService;
import com.linkplaza.vo.CustomLinkAnalytic;
import com.linkplaza.vo.PageAnalytic;
import com.linkplaza.vo.SocialLinkAnalytic;
import com.linkplaza.vo.Timeserie;

@Service
public class AnalyticServiceImpl implements IAnalyticService {
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private SocialLinkClickRepository socialLinkClickRepository;
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
    public void logSocialLinkClick(Long socialLinkId, String ipAddress) {
        SocialLink socialLink = pageService.getSocialLinkById(socialLinkId);

        SocialLinkClick socialLinkClick = new SocialLinkClick();
        socialLinkClick.setSocialLink(socialLink);
        socialLinkClick.setIpAddress(ipAddress);
        socialLinkClick.setDateCreated(new Date());
        socialLinkClickRepository.save(socialLinkClick);
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
            timeserie.setSocialLinkClicks(0L);
            timeserie.setSocialLinkUniqueClicks(0L);
            timeserie.setCustomLinkClicks(0L);
            timeserie.setCustomLinkUniqueClicks(0L);
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
        List<CustomLinkClick> customLinkClicks = customLinkClickRepository.findByCustomLink_PageAndDateCreatedBetween(page, startDate, endDate);
        List<SocialLinkClick> socialLinkClicks = socialLinkClickRepository.findBySocialLink_PageAndDateCreatedBetween(page, startDate, endDate);
        // rastrear las ip unicas de clicks por fecha
        Map<String, Set<String>> uniqueIpSocialLinkClicksMap = new HashMap<>();
        Map<String, Set<String>> uniqueIpCustomLinkClicksMap = new HashMap<>();

        for (SocialLinkClick socialLinkClick : socialLinkClicks) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(socialLinkClick.getDateCreated());
            uniqueIpSocialLinkClicksMap.putIfAbsent(date, new HashSet<>());

            for (Timeserie timeserie : timeseries) {
                if (timeserie.getDate().equals(date)) {
                    timeserie.setSocialLinkClicks(timeserie.getSocialLinkClicks() + 1);
                    uniqueIpSocialLinkClicksMap.get(date).add(socialLinkClick.getIpAddress());
                    break;
                }
            }

        }

        for (CustomLinkClick customLinkClick : customLinkClicks) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(customLinkClick.getDateCreated());
            uniqueIpCustomLinkClicksMap.putIfAbsent(date, new HashSet<>());

            for (Timeserie timeserie : timeseries) {
                if (timeserie.getDate().equals(date)) {
                    timeserie.setCustomLinkClicks(timeserie.getCustomLinkClicks() + 1);
                    uniqueIpCustomLinkClicksMap.get(date).add(customLinkClick.getIpAddress());
                    break;
                }
            }

        }

        // setear conteo de ips unicas para views y clicks por fecha
        for (Timeserie timeserie : timeseries) {
            Set<String> uniqueIpViews = uniqueIpViewsMap.get(timeserie.getDate());
            Set<String> uniqueIpSocialLinkClicks = uniqueIpSocialLinkClicksMap.get(timeserie.getDate());
            Set<String> uniqueIpCustomLinkClicks = uniqueIpCustomLinkClicksMap.get(timeserie.getDate());
            if (uniqueIpViews != null) {
                timeserie.setUniqueViews((long) uniqueIpViews.size());
            }
            if (uniqueIpSocialLinkClicks != null) {
                timeserie.setSocialLinkUniqueClicks((long) uniqueIpSocialLinkClicks.size());
            }
            if (uniqueIpCustomLinkClicks != null) {
                timeserie.setCustomLinkUniqueClicks((long) uniqueIpCustomLinkClicks.size());
            }
        }

        // conteo de clicks para cada link
        List<SocialLinkAnalytic> socialLinkAnalytics = new ArrayList<>();
        List<CustomLinkAnalytic> customLinkAnalytics = new ArrayList<>();

        for (SocialLink socialLink : page.getSocialLinks()) {
            Long clicks = socialLinkClickRepository.countBySocialLink(socialLink);
            Long uniqueClicks = socialLinkClickRepository.countDistinctByIpAddressAndSocialLink(socialLink);

            SocialLinkAnalytic socialLinkAnalytic = new SocialLinkAnalytic();
            socialLinkAnalytic.setSocialLink(socialLink);
            socialLinkAnalytic.setClicks(clicks);
            socialLinkAnalytic.setUniqueClicks(uniqueClicks);
            socialLinkAnalytics.add(socialLinkAnalytic);
        }

        for (CustomLink customLink : page.getCustomLinks()) {
            Long clicks = customLinkClickRepository.countByCustomLink(customLink);
            Long uniqueClicks = customLinkClickRepository.countDistinctByIpAddressAndCustomLink(customLink);

            CustomLinkAnalytic customLinkAnalytic = new CustomLinkAnalytic();
            customLinkAnalytic.setCustomLink(customLink);
            customLinkAnalytic.setClicks(clicks);
            customLinkAnalytic.setUniqueClicks(uniqueClicks);
            customLinkAnalytics.add(customLinkAnalytic);
        }

        // conteo total de eventos relacionados a la pagina
        Long totalViews = visitRepository.countByPage(page);
        Long totalUniqueViews = visitRepository.countDistinctByIpAddressAndPage(page);
        Long totalSocialLinkClicks = socialLinkClickRepository.countBySocialLink_Page(page);
        Long totalSocialLinkUniqueClicks = socialLinkClickRepository.countDistinctByIpAddressAndSocialLinkPage(page);
        Long totalCustomLinkClicks = customLinkClickRepository.countByCustomLink_Page(page);
        Long totalCustomLinkUniqueClicks = customLinkClickRepository.countDistinctByIpAddressAndCustomLinkPage(page);

        PageAnalytic pageAnalytic = new PageAnalytic();
        pageAnalytic.setTotalViews(totalViews);
        pageAnalytic.setTotalUniqueViews(totalUniqueViews);
        pageAnalytic.setTotalSocialLinkClicks(totalSocialLinkClicks);
        pageAnalytic.setTotalSocialLinkUniqueClicks(totalSocialLinkUniqueClicks);
        pageAnalytic.setTotalCustomLinkClicks(totalCustomLinkClicks);
        pageAnalytic.setTotalCustomLinkUniqueClicks(totalCustomLinkUniqueClicks);
        pageAnalytic.setTimeseries(timeseries);
        pageAnalytic.setCustomLinkAnalytics(customLinkAnalytics);
        pageAnalytic.setSocialLinkAnalytics(socialLinkAnalytics);

        return pageAnalytic;

    }

}
