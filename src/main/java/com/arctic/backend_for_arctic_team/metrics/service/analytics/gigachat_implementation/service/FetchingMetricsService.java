//package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;
//
//import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.dto.MetricsForAnalyticsDtoV2;
//import com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.repository.FetchingMetricsForAnalyticsRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class FetchingMetricsService {
//
//    private final FetchingMetricsForAnalyticsRepository fetchingMetricsForAnalyticsRepository;
//
//    public List<MetricsForAnalyticsDtoV2> getMetricsForAnalytics(String indNum, Long expeditionId){
//        log.debug("Starting fetching metrics started for indnum: {}, expeditionId: {}", indNum, expeditionId);
//
//        return fetchingMetricsForAnalyticsRepository.getMetricsForAnalyticsBySession(indNum, expeditionId);
//
//    }
//
//
//}
