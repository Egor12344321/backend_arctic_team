package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import jakarta.validation.Valid;

import java.util.List;

public record UploadRequest(
        @Valid List<CardioMetricDto> cardioMetrics,
        @Valid List<EmotionalMetricDto> emotionalMetrics,
        @Valid List<MemsMetricDto> memsMetrics,
        @Valid List<NfbMetricDto> nfbMetrics,
        @Valid List<PhysiologicalMetricDto> physiologicalMetrics,
        @Valid List<ProductivityMetricDto> productivityMetrics,
        List<EEGArtifactsMetricDto> EEGArtifactsMetrics,
        List<EEGProceedMetricDto> EEGProceedMetrics,
        List<EEGRawMetricDto> EEGRawMetrics,
        @Valid List<CardioMetricCompressedDto> cardioMetricsCompressed,
        @Valid List<EmotionalMetricCompressedDto> emotionalMetricsCompressed,
        @Valid List<MemsMetricCompressedDto> memsMetricsCompressed,
        @Valid List<NfbMetricCompressedDto> nfbMetricsCompressed,
        @Valid List<PhysiologicalMetricCompressedDto> physiologicalMetricsCompressed,
        @Valid List<ProductivityMetricCompressedDto> productivityMetricsCompressed,
        List<EEGArtifactsMetricCompressedDto> EEGArtifactsMetricsCompressed,
        List<EEGProceedMetricCompressedDto> EEGProceedMetricsCompressed,
        List<EEGRawMetricCompressedDto> EEGRawMetricsCompressed,
        List<PhysiologicalBaselineDto> physiologicalBaseline,
        List<ProductivityBaselineDto> productivityBaseline,
        List<ProductivityIndexDto> productivityIndex

) {}
