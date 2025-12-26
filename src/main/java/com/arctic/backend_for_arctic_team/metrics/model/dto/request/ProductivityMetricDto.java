package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetrics;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductivityMetricDto(
        String individualNumber,
        Long timestamp,
        Integer session,
        Double gravity,
        Double productivity,
        Double fatigue,
        Double reverseFatigue,
        Double relaxation,
        Double concentration
) {
    public static ProductivityMetrics mapToProductivityEntity(ProductivityMetricDto dto) {
        return ProductivityMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
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
