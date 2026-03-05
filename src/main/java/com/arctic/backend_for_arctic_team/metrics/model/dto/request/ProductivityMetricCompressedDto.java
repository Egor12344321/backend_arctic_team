package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetricsCompressed;

public record ProductivityMetricCompressedDto(
        String individualNumber,
        Long timestamp,
        String expeditionId,
        Integer session,
        Double gravity,
        Double productivity,
        Double fatigue,
        Double reverseFatigue,
        Double relaxation,
        Double concentration
) {
    public static ProductivityMetricsCompressed mapToProductivityEntity(ProductivityMetricCompressedDto dto) {
        return ProductivityMetricsCompressed.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(dto.expeditionId)
                .session(dto.session())
                .gravity(dto.gravity())
                .productivity(dto.productivity())
                .fatigue(dto.fatigue())
                .reverseFatigue(dto.reverseFatigue())
                .relaxation(dto.relaxation())
                .concentration(dto.concentration())
                .build();
    }
}
