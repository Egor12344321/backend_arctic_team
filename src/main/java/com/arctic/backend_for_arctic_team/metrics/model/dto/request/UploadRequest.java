package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public record UploadRequest(
        @Valid List<CardioMetricDto> cardioMetrics,
        @Valid List<EmotionalMetricDto> emotionalMetrics,
        @Valid List<MemsMetricDto> memsMetrics,
        @Valid List<NfbMetricDto> nfbMetrics,
        @Valid List<PhysiologicalMetricDto> physiologicalMetrics,
        @Valid List<ProductivityMetricDto> productivityMetrics
) {}
