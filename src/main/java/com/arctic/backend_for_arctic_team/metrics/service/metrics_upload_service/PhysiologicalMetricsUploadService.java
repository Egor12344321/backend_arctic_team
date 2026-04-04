package com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.PhysiologicalMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalMetrics;
import com.arctic.backend_for_arctic_team.metrics.repository.jpa.PhysiologicalMetricsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhysiologicalMetricsUploadService {
    private final PhysiologicalMetricsRepository physiologicalMetricsRepository;

    public void upload(@Valid List<PhysiologicalMetricDto> physiologicalMetricDtos) {
        List<PhysiologicalMetrics> physiologicalMetrics = physiologicalMetricDtos.stream()
                .map(PhysiologicalMetricDto::mapToPhysiologicalEntity)
                .toList();
        physiologicalMetricsRepository.saveAll(physiologicalMetrics);
    }
}
