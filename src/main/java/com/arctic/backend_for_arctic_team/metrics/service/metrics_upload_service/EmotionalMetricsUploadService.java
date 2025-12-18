package com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.EmotionalMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.dto.request.ProductivityMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EmotionalMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetrics;
import com.arctic.backend_for_arctic_team.metrics.repository.EmotionalMetricsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmotionalMetricsUploadService {
    private final EmotionalMetricsRepository emotionalMetricsRepository;

    public void upload(@Valid List<EmotionalMetricDto> emotionalMetricDtos) {
        List<EmotionalMetrics> emotionalMetrics = emotionalMetricDtos.stream()
                .map(EmotionalMetricDto::mapToEmotionalEntity)
                .toList();
        emotionalMetricsRepository.saveAll(emotionalMetrics);
    }
}
