package com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.CardioMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;
import com.arctic.backend_for_arctic_team.metrics.repository.CardioMetricsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardioMetricsUploadService {

    private final CardioMetricsRepository repository;

    public void upload(@Valid List<CardioMetricDto> cardioMetricDtos) {
        for (CardioMetricDto dto : cardioMetricDtos){
            CardioMetrics cardioMetrics = CardioMetricDto.mapToCardioEntity(dto);
            repository.save(cardioMetrics);
        }
    }
}
