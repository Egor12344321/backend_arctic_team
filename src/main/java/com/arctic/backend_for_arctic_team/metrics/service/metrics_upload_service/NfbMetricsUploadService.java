package com.arctic.backend_for_arctic_team.metrics.service.metrics_upload_service;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.NfbMetricDto;
import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;
import com.arctic.backend_for_arctic_team.metrics.repository.NfbMetricsRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NfbMetricsUploadService {
    private final NfbMetricsRepository nfbMetricsRepository;

    public void upload(@Valid List<NfbMetricDto> nfbMetricDtos) {
        for (NfbMetricDto dto : nfbMetricDtos){
            NfbMetrics nfbMetrics = NfbMetricDto.mapToNfbEntity(dto);
            nfbMetricsRepository.save(nfbMetrics);
        }
    }
}
