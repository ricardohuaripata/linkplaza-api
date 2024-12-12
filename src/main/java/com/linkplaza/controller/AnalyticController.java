package com.linkplaza.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkplaza.service.IAnalyticService;
import com.linkplaza.vo.PageAnalytic;

@RestController
@RequestMapping("/api/v1/analytic")
public class AnalyticController {
    @Autowired
    private IAnalyticService analyticService;

    @Value("${api.key}")
    private String apiKey;

    @PostMapping("/visit")
    public ResponseEntity<?> logVisit(@RequestParam Long pageId, HttpServletRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String apiKey) {
        // verificar la API key
        if (!isValidApiKey(apiKey)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String ipAddress = getClientIp(request);
        analyticService.logVisit(pageId, ipAddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/visit")
    public ResponseEntity<?> getPageVisitsByDateRange(@RequestParam Long pageId,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String apiKey) {
        // verificar la API key
        if (!isValidApiKey(apiKey)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Date start;
        Date end;

        try {
            // convertir las fechas de String a Date
            start = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(startDate);
            end = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(endDate);
        } catch (Exception e) {
            // en caso de error, establecer el rango de fechas a los ultimos 7 dias
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -7); // restar 7 dias
            start = calendar.getTime();
            end = new Date(); // fecha actual
        }

        PageAnalytic pageAnalytic = analyticService.getPageAnalytic(pageId, start, end);
        return new ResponseEntity<>(pageAnalytic, HttpStatus.OK);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private boolean isValidApiKey(String apiKey) {
        return this.apiKey.equals(apiKey);
    }

}
