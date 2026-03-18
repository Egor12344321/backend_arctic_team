package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.MemsMetricsCompressed;

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
    public static MemsMetricsCompressed mapToMemsEntity(MemsMetricCompressedDto dto) {
        return MemsMetricsCompressed.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(Long.valueOf(dto.expeditionId))
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
