package com.linkplaza.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import com.linkplaza.vo.VisitCount;

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

        // Crear un mapa para contar las visitas por fecha
        Map<String, VisitCount> visitsCount = new LinkedHashMap<>();

        // Inicializar el calendario para recorrer las fechas
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        // Recorrer cada día entre startDate y endDate
        while (!calendar.getTime().after(endDate)) {
            String dateKey = new SimpleDateFormat("dd-MM-yyyy").format(calendar.getTime());
            visitsCount.put(dateKey, new VisitCount(0L, 0L)); // Inicializar con 0 visitas y 0 visitas únicas
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Avanzar un día
        }

        // Contar las visitas por fecha
        List<Visit> visits = visitRepository.findByPageAndDateCreatedBetween(page, startDate, endDate);
        Set<String> uniqueIps = new HashSet<>();
        for (Visit visit : visits) {
            String dateKey = new SimpleDateFormat("dd-MM-yyyy").format(visit.getDateCreated());
            visitsCount.get(dateKey).setViews(visitsCount.get(dateKey).getViews() + 1);

            // Agregar IP a la lista de únicas
            uniqueIps.add(visit.getIpAddress());
            visitsCount.get(dateKey).setUniqueViews((long) uniqueIps.size());
        }

        PageAnalytic pageAnalytic = new PageAnalytic();
        pageAnalytic.setTimeseries(visitsCount);
        pageAnalytic.setTotalViews(visitRepository.countByPage(page));
        pageAnalytic.setTotalUniqueViews(visitRepository.countDistinctByIpAddressAndPage(page));

        return pageAnalytic;

    }

}
