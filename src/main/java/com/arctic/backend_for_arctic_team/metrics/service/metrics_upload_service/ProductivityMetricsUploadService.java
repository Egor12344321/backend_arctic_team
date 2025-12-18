package com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.ProductivityMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetrics;
import com.arctic.backend_for_arctic_team.metrics.repository.ProductivityMetricsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductivityMetricsUploadService {
    private final ProductivityMetricsRepository productivityMetricsRepository;

    @Transactional
    public void upload(@Valid List<ProductivityMetricDto> productivityMetricDtos) {
        List<ProductivityMetrics> productivityMetrics = productivityMetricDtos.stream()
                        .map(ProductivityMetricDto::mapToProductivityEntity)
                        .toList();
        productivityMetricsRepository.saveAll(productivityMetrics);
    }
}
