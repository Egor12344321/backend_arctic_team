package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetrics;

public record MemsMetricCompressedDto(
        String individualNumber,
        String expeditionId,
        Long timestamp,
        Integer session,
        Double accelerometerX,
        Double accelerometerY,
        Double accelerometerZ,
        Double gyroscopeX,
        Double gyroscopeY,
        Double gyroscopeZ
) {
    public static MemsMetrics mapToMemsEntity(MemsMetricCompressedDto dto) {
        return MemsMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(dto.expeditionId)
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
