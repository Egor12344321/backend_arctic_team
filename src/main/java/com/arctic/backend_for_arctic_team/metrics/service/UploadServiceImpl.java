package com.arctic.backend_for_arctic_team.metrics.service;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.*;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;
import com.arctic.backend_for_arctic_team.metrics.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService{
    private final CardioMetricsRepository cardioMetricsRepository;
    private final EmotionalMetricsRepository emotionalMetricsRepository;
    private final MemsMetricsRepository memsMetricsRepository;
    private final NfbMetricsRepository nfbMetricsRepository;
    private final PhysiologicalMetricsRepository physiologicalMetricsRepository;
    private final ProductivityMetricsRepository productivityMetricsRepository;
    private final EegArtifactsMetricsRepository eegArtifactsMetricsRepository;
    private final EegProceedMetricsRepository eegProceedMetricsRepository;
    private final EegRawMetricsRepository eegRawMetricsRepository;

    @Override
    public UploadResponse uploadMetrics(UploadRequest uploadRequest) {
        log.info("Mapping from dto to entity");
        log.info("METRICS: {}", uploadRequest);
        log.info("EEGArtifacts: {}, EEGProceed: {}, EEGRaw: {}", uploadRequest.EEGArtifactsMetrics(), uploadRequest.EEGProceedMetrics(), uploadRequest.EEGRawMetrics());
        try {
            uploadBatch(uploadRequest.cardioMetrics(), cardioMetricsRepository, CardioMetricDto::mapToCardioEntity);
            uploadBatch(uploadRequest.emotionalMetrics(), emotionalMetricsRepository, EmotionalMetricDto::mapToEmotionalEntity);
            uploadBatch(uploadRequest.memsMetrics(), memsMetricsRepository, MemsMetricDto::mapToMemsEntity);
            uploadBatch(uploadRequest.nfbMetrics(), nfbMetricsRepository, NfbMetricDto::mapToNfbEntity);
            uploadBatch(uploadRequest.EEGArtifactsMetrics(), eegArtifactsMetricsRepository, EEGArtifactsMetricDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGProceedMetrics(), eegProceedMetricsRepository, EEGProceedMetricDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGRawMetrics(), eegRawMetricsRepository, EEGRawMetricDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.physiologicalMetrics(), physiologicalMetricsRepository, PhysiologicalMetricDto::mapToPhysiologicalEntity);
            uploadBatch(uploadRequest.productivityMetrics(), productivityMetricsRepository, ProductivityMetricDto::mapToProductivityEntity);

            return new UploadResponse(true);
        } catch (Exception e){
            log.info("UPLOADING-EXCEPTION: {}", e.getMessage());
            return new UploadResponse(false);
        }
    }

    @Transactional
    private <T, D> void uploadBatch(List<D> dtos, JpaRepository<T, Long> repository, Function<D, T> mapper){
        if (dtos == null || dtos.isEmpty()) return;

        List<T> entities = dtos.stream()
                .map(mapper)
                .toList();

        repository.saveAll(entities);
    }
}
