package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGProceedMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGProceedMetricsCompressed;

public record EEGProceedMetricCompressedDto(
        String individualNumber,
        String expeditionId,
        Long timestamp,
        Integer session,
        Float channel1,
        Float channel2
) {
    public static EEGProceedMetricsCompressed mapFromRequestToEntity(EEGProceedMetricCompressedDto request){
        return EEGProceedMetricsCompressed.builder()
                .expeditionId(request.expeditionId)
                .channel1(request.channel1())
                .channel2(request.channel2())
                .individualNumber(request.individualNumber())
                .session(request.session())
                .timestamp(request.timestamp())
                .build();
    }
}
