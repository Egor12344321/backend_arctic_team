package com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.MemsMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetrics;
import com.arctic.backend_for_arctic_team.metrics.repository.MemsMetricsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemsMetricsUploadService {
    private final MemsMetricsRepository memsMetricsRepository;

    public void upload(@Valid List<MemsMetricDto> memsMetricDtos) {
        for (MemsMetricDto dto : memsMetricDtos){
            MemsMetrics memsMetrics = MemsMetricDto.mapToMemsEntity(dto);
            memsMetricsRepository.save(memsMetrics);
        }
    }
}
