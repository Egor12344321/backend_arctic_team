package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetricsCompressed;

public record CardioMetricCompressedDto(
        String individualNumber,
        Long timestamp,
        String expeditionId,
        Integer session,
        Double heartRate,
        Integer hasArtifacts,
        Double kaplanIndex,
        Integer metricsAvailable,
        Integer motionArtifacts,
        Integer skinContact,
        Double stressIndex
) {
    public static CardioMetricsCompressed mapToCardioEntity(CardioMetricCompressedDto dto) {
        return CardioMetricsCompressed.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(Long.valueOf(dto.expeditionId()))
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
