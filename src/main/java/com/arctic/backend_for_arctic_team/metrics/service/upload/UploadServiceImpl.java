package com.arctic.backend_for_arctic_team.metrics.service.upload;


import com.arctic.backend_for_arctic_team.metrics.model.dto.request.*;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;
import com.arctic.backend_for_arctic_team.metrics.repository.jpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {
    private final CardioMetricsRepository cardioMetricsRepository;
    private final EmotionalMetricsRepository emotionalMetricsRepository;
    private final MemsMetricsRepository memsMetricsRepository;
    private final NfbMetricsRepository nfbMetricsRepository;
    private final PhysiologicalMetricsRepository physiologicalMetricsRepository;
    private final ProductivityMetricsRepository productivityMetricsRepository;
    private final EegArtifactsMetricsRepository eegArtifactsMetricsRepository;
    private final EegProceedMetricsRepository eegProceedMetricsRepository;
    private final EegRawMetricsRepository eegRawMetricsRepository;
    private final CardioMetricsCompressedRepository cardioMetricsCompressedRepository;
    private final EmotionalMetricsCompressedRepository emotionalMetricsCompressedRepository;
    private final MemsMetricsCompressedRepository memsMetricsCompressedRepository;
    private final NfbMetricsCompressedRepository nfbMetricsCompressedRepository;
    private final EegArtifactsMetricsCompressedRepository eegArtifactsMetricsCompressedRepository;
    private final EegProceedMetricsCompressedRepository eegProceedMetricsCompressedRepository;
    private final EegRawMetricsCompressedRepository eegRawMetricsCompressedRepository;
    private final PhysiologicalMetricsCompressedRepository physiologicalMetricsCompressedRepository;
    private final ProductivityMetricsCompressedRepository productivityMetricsCompressedRepository;
    private final PhysiologicalBaselineRepository physiologicalBaselineRepository;
    private final ProductivityBaselineRepository productivityBaselineRepository;
    private final ProductivityIndexRepository productivityIndexRepository;

    @Override
    public UploadResponse uploadMetrics(UploadRequest uploadRequest) {
        log.info("Mapping from dto to entity");
        log.info("Cardio Metrics: {} записей", sizeOrZero(uploadRequest.cardioMetrics()));
        log.info("Emotional Metrics: {} записей", sizeOrZero(uploadRequest.emotionalMetrics()));
        log.info("MEMS Metrics: {} записей", sizeOrZero(uploadRequest.memsMetrics()));
        log.info("NFB Metrics: {} записей", sizeOrZero(uploadRequest.nfbMetrics()));
        log.info("EEG Artifacts Metrics: {} записей", sizeOrZero(uploadRequest.EEGArtifactsMetrics()));
        log.info("EEG Proceed Metrics: {} записей", sizeOrZero(uploadRequest.EEGProceedMetrics()));
        log.info("EEG Raw Metrics: {} записей", sizeOrZero(uploadRequest.EEGRawMetrics()));
        log.info("Physiological Metrics: {} записей", sizeOrZero(uploadRequest.physiologicalMetrics()));
        log.info("Productivity Metrics: {} записей", sizeOrZero(uploadRequest.productivityMetrics()));

        log.info("===== СЖАТЫЕ МЕТРИКИ =====");
        log.info("Cardio Metrics Compressed: {} записей", sizeOrZero(uploadRequest.cardioMetricsCompressed()));
        log.info("Emotional Metrics Compressed: {} записей", sizeOrZero(uploadRequest.emotionalMetricsCompressed()));
        log.info("MEMS Metrics Compressed: {} записей", sizeOrZero(uploadRequest.memsMetricsCompressed()));
        log.info("NFB Metrics Compressed: {} записей", sizeOrZero(uploadRequest.nfbMetricsCompressed()));
        log.info("EEG Artifacts Metrics Compressed: {} записей", sizeOrZero(uploadRequest.EEGArtifactsMetricsCompressed()));
        log.info("EEG Proceed Metrics Compressed: {} записей", sizeOrZero(uploadRequest.EEGProceedMetricsCompressed()));
        log.info("EEG Raw Metrics Compressed: {} записей", sizeOrZero(uploadRequest.EEGRawMetricsCompressed()));
        log.info("Physiological Metrics Compressed: {} записей", sizeOrZero(uploadRequest.physiologicalMetricsCompressed()));
        log.info("Productivity Metrics Compressed: {} записей", sizeOrZero(uploadRequest.productivityMetricsCompressed()));

        log.info("===== БАЗОВЫЕ ПОКАЗАТЕЛИ =====");
        log.info("Physiological Baseline: {} записей", sizeOrZero(uploadRequest.physiologicalBaseline()));
        log.info("Productivity Baseline: {} записей", sizeOrZero(uploadRequest.productivityBaseline()));
        log.info("Productivity Index: {} записей", sizeOrZero(uploadRequest.productivityIndex()));

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
            uploadBatch(uploadRequest.cardioMetricsCompressed(), cardioMetricsCompressedRepository, CardioMetricCompressedDto::mapToCardioEntity);
            uploadBatch(uploadRequest.emotionalMetricsCompressed(), emotionalMetricsCompressedRepository, EmotionalMetricCompressedDto::mapToEmotionalEntity);
            uploadBatch(uploadRequest.memsMetricsCompressed(), memsMetricsCompressedRepository, MemsMetricCompressedDto::mapToMemsEntity);
            uploadBatch(uploadRequest.nfbMetricsCompressed(), nfbMetricsCompressedRepository, NfbMetricCompressedDto::mapToNfbEntity);
            uploadBatch(uploadRequest.EEGArtifactsMetricsCompressed(), eegArtifactsMetricsCompressedRepository, EEGArtifactsMetricCompressedDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGProceedMetricsCompressed(), eegProceedMetricsCompressedRepository, EEGProceedMetricCompressedDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGRawMetricsCompressed(), eegRawMetricsCompressedRepository, EEGRawMetricCompressedDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.physiologicalMetricsCompressed(), physiologicalMetricsCompressedRepository, PhysiologicalMetricCompressedDto::mapToPhysiologicalEntity);
            uploadBatch(uploadRequest.productivityMetricsCompressed(), productivityMetricsCompressedRepository, ProductivityMetricCompressedDto::mapToProductivityEntity);
            uploadBatch(uploadRequest.physiologicalBaseline(), physiologicalBaselineRepository, PhysiologicalBaselineDto::toEntity);
            uploadBatch(uploadRequest.productivityBaseline(), productivityBaselineRepository, ProductivityBaselineDto::toEntity);
            uploadBatch(uploadRequest.productivityIndex(), productivityIndexRepository, ProductivityIndexDto::toEntity);

            return new UploadResponse(true);
        } catch (Exception e){
            log.info("UPLOADING-EXCEPTION: {}", e.getMessage());
            return new UploadResponse(false);
        }
    }



    protected <T, D> void uploadBatch(List<D> dtos, JpaRepository<T, Long> repository, Function<D, T> mapper){
        if (dtos == null || dtos.isEmpty()) return;

        List<T> entities = dtos.stream()
                .map(mapper)
                .toList();

        repository.saveAll(entities);
    }
    private int sizeOrZero(List<?> list) {
        return list != null ? list.size() : 0;
    }
}
