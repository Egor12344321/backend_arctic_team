package com.arctic.backend_for_arctic_team.metrics.service.upload;

import com.arctic.backend_for_arctic_team.metrics.model.dto.request.*;
import com.arctic.backend_for_arctic_team.metrics.model.dto.response.UploadResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UploadResponse uploadMetrics(UploadRequest uploadRequest) {
        log.info("Начало загрузки метрик");

        try {
            uploadBatch(uploadRequest.cardioMetrics(), CardioMetricDto::mapToCardioEntity);
            uploadBatch(uploadRequest.emotionalMetrics(), EmotionalMetricDto::mapToEmotionalEntity);
            uploadBatch(uploadRequest.memsMetrics(), MemsMetricDto::mapToMemsEntity);
            uploadBatch(uploadRequest.nfbMetrics(), NfbMetricDto::mapToNfbEntity);
            uploadBatch(uploadRequest.EEGArtifactsMetrics(), EEGArtifactsMetricDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGProceedMetrics(), EEGProceedMetricDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGRawMetrics(), EEGRawMetricDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.physiologicalMetrics(), PhysiologicalMetricDto::mapToPhysiologicalEntity);
            uploadBatch(uploadRequest.productivityMetrics(), ProductivityMetricDto::mapToProductivityEntity);
            uploadBatch(uploadRequest.cardioMetricsCompressed(), CardioMetricCompressedDto::mapToCardioEntity);
            uploadBatch(uploadRequest.emotionalMetricsCompressed(), EmotionalMetricCompressedDto::mapToEmotionalEntity);
            uploadBatch(uploadRequest.memsMetricsCompressed(), MemsMetricCompressedDto::mapToMemsEntity);
            uploadBatch(uploadRequest.nfbMetricsCompressed(), NfbMetricCompressedDto::mapToNfbEntity);
            uploadBatch(uploadRequest.EEGArtifactsMetricsCompressed(), EEGArtifactMetricCompressedDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGProceedMetricsCompressed(), EEGProceedMetricCompressedDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.EEGRawMetricsCompressed(), EEGRawMetricCompressedDto::mapFromRequestToEntity);
            uploadBatch(uploadRequest.physiologicalMetricsCompressed(), PhysiologicalMetricCompressedDto::mapToPhysiologicalEntity);
            uploadBatch(uploadRequest.productivityMetricsCompressed(), ProductivityMetricCompressedDto::mapToProductivityEntity);
            uploadBatch(uploadRequest.physiologicalBaseline(), PhysiologicalBaselineDto::toEntity);
            uploadBatch(uploadRequest.productivityBaseline(), ProductivityBaselineDto::toEntity);
            uploadBatch(uploadRequest.productivityIndex(), ProductivityIndexDto::toEntity);
            uploadBatch(uploadRequest.sessionResults(), SessionDto::toEntity);

            log.info("Загрузка метрик успешно завершена");
            return new UploadResponse(true);
        } catch (Exception e) {
            log.error("Ошибка при загрузке метрик: {}", e.getMessage(), e);
            return new UploadResponse(false);
        }
    }

    private <T, D> void uploadBatch(List<D> dtos, Function<D, T> mapper) {
        if (dtos == null || dtos.isEmpty()) return;

        int batchSize = 50;
        int counter = 0;

        for (D dto : dtos) {
            T entity = mapper.apply(dto);
            entityManager.persist(entity);
            counter++;

            if (counter % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        if (counter % batchSize != 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }
}