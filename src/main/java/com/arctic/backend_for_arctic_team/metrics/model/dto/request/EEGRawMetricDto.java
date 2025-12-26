package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetrics;

public record EEGRawMetricDto(
        String individualNumber,
        Long timestamp,
        Integer session,
        Float channel1,
        Float channel2
) {
    public static EEGRawMetrics mapFromRequestToEntity(EEGRawMetricDto request){
        return EEGRawMetrics.builder()
                .channel1(request.channel1())
                .channel2(request.channel2())
                .individualNumber(request.individualNumber())
                .session(request.session())
                .timestamp(request.timestamp())
                .build();
    }
}
