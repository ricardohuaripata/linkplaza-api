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

import com.linkplaza.entity.Page;
import com.linkplaza.entity.Visit;
import com.linkplaza.repository.VisitRepository;
import com.linkplaza.service.IEventService;
import com.linkplaza.service.IPageService;
import com.linkplaza.vo.PageAnalytic;
import com.linkplaza.vo.Timeserie;

@Service
public class EventServiceImpl implements IEventService {
    @Autowired
    private VisitRepository visitRepository;
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
    public PageAnalytic getPageAnalytic(Long pageId, Date startDate, Date endDate) {
        Page page = pageService.getPageById(pageId);

        List<Timeserie> timeseries = new ArrayList<>();

        // inicializar el calendario para recorrer las fechas
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // recorrer cada dia entre startDate y endDate
        while (!calendar.getTime().after(endDate)) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
            Timeserie timeserie = new Timeserie();
            timeserie.setViews(0L);
            timeserie.setUniqueViews(0L);
            timeserie.setDate(date);
            timeseries.add(timeserie);

            calendar.add(Calendar.DAY_OF_MONTH, 1); // avanza un dia
        }

        // obtener los registros de visitas del rango de fechas
        List<Visit> visits = visitRepository.findByPageAndDateCreatedBetween(page, startDate, endDate);

        // crear un mapa para rastrear las ip unicas por fecha
        Map<String, Set<String>> uniqueIpMap = new HashMap<>();

        for (Visit visit : visits) {
            String date = new SimpleDateFormat("dd-MM-yyyy").format(visit.getDateCreated());
            uniqueIpMap.putIfAbsent(date, new HashSet<>());

            for (Timeserie timeserie : timeseries) {
                if (timeserie.getDate().equals(date)) {
                    timeserie.setViews(timeserie.getViews() + 1);
                    uniqueIpMap.get(date).add(visit.getIpAddress());
                    break;
                }
            }

        }

        for (Timeserie timeserie : timeseries) {
            Set<String> uniqueIps = uniqueIpMap.get(timeserie.getDate());
            if (uniqueIps != null) {
                timeserie.setUniqueViews((long) uniqueIps.size());
            }
        }

        PageAnalytic pageAnalytic = new PageAnalytic();
        pageAnalytic.setTotalViews(visitRepository.countByPage(page));
        pageAnalytic.setTotalUniqueViews(visitRepository.countDistinctByIpAddressAndPage(page));
        pageAnalytic.setTimeseries(timeseries);

        return pageAnalytic;

    }

}
