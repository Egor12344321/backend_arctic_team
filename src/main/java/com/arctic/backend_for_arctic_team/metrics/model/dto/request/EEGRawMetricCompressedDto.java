package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetricsCompressed;

public record EEGRawMetricCompressedDto(
        String individualNumber,
        String expeditionId,
        Long timestamp,
        Integer session,
        Float channel1,
        Float channel2
) {
    public static EEGRawMetricsCompressed mapFromRequestToEntity(EEGRawMetricCompressedDto request){
        return EEGRawMetricsCompressed.builder()
                .channel1(request.channel1())
                .channel2(request.channel2())
                .expeditionId(Long.valueOf(request.expeditionId))
                .individualNumber(request.individualNumber())
                .session(request.session())
                .timestamp(request.timestamp())
                .build();
    }
}
