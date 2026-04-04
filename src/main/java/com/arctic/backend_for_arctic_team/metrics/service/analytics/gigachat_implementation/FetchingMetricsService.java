package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation;

import com.arctic.backend_for_arctic_team.metrics.repository.jpa.CardioMetricsRepository;
import com.arctic.backend_for_arctic_team.metrics.repository.jpa.NfbMetricsRepository;
import com.arctic.backend_for_arctic_team.metrics.repository.jpa.PhysiologicalMetricsRepository;
import com.arctic.backend_for_arctic_team.metrics.repository.jpa.ProductivityMetricsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FetchingMetricsService {

    private final NfbMetricsRepository nfbMetricsRepository;
    private final PhysiologicalMetricsRepository physiologicalMetricsRepository;
    private final CardioMetricsRepository cardioMetricsRepository;
    private final ProductivityMetricsRepository productivityMetricsRepository;

    public MetricsForAnalyticsDto getMetricsForAnalytics(String indNum, Long expeditionId){
        log.debug("Starting fetching metrics started for indnum: {}, expeditionId: {}", indNum, expeditionId);
        return MetricsForAnalyticsDto.builder()
                .nfbMetrics(nfbMetricsRepository.findAllByIndividualNumberAndExpeditionId(indNum, expeditionId))
                .physiologicalMetrics(physiologicalMetricsRepository.findAllByIndividualNumberAndExpeditionId(indNum, expeditionId))
                .productivityMetrics(productivityMetricsRepository.findAllByIndividualNumberAndExpeditionId(indNum, expeditionId))
                .cardioMetrics(cardioMetricsRepository.findAllByIndividualNumberAndExpeditionId(indNum, expeditionId))
                .build();

    }


}
