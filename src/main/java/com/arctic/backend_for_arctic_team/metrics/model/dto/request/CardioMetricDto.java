package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CardioMetricDto(
        String individualNumber,
        Long timestamp,
        Integer session,
        Double heartRate,
        Integer hasArtifacts,
        Double kaplanIndex,
        Integer metricsAvailable,
        Integer motionArtifacts,
        Integer skinContact,
        Double stressIndex
) {
    public static CardioMetrics mapToCardioEntity(CardioMetricDto dto) {
        return CardioMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .session(dto.session())
                .heartRate(dto.heartRate())
                .hasArtifacts(dto.hasArtifacts())
                .kaplanIndex(dto.kaplanIndex())
                .metricsAvailable(dto.metricsAvailable())
                .motionArtifacts(dto.motionArtifacts())
                .skinContact(dto.skinContact())
                .stressIndex(dto.stressIndex())
                .build();
    }
}
