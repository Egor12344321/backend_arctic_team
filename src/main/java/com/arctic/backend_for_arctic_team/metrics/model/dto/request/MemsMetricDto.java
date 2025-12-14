package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetrics;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemsMetricDto(
        @NotBlank String individualNumber,
        @NotNull Long timestamp,
        Integer session,
        Double accelerometerX,
        Double accelerometerY,
        Double accelerometerZ,
        Double gyroscopeX,
        Double gyroscopeY,
        Double gyroscopeZ
) {
    public static MemsMetrics mapToMemsEntity(MemsMetricDto dto) {
        return MemsMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .session(dto.session())
                .accelerometerX(dto.accelerometerX())
                .accelerometerY(dto.accelerometerY())
                .accelerometerZ(dto.accelerometerZ())
                .gyroscopeX(dto.gyroscopeX())
                .gyroscopeY(dto.gyroscopeY())
                .gyroscopeZ(dto.gyroscopeZ())
                .build();
    }
}
