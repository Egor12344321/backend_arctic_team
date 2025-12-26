package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGProceedMetrics;

public record EEGProceedMetricDto(
        String individualNumber,
        Long timestamp,
        Integer session,
        Float channel1,
        Float channel2
) {
    public static EEGProceedMetrics mapFromRequestToEntity(EEGProceedMetricDto request){
        return EEGProceedMetrics.builder()
                .channel1(request.channel1())
                .channel2(request.channel2())
                .individualNumber(request.individualNumber())
                .session(request.session())
                .timestamp(request.timestamp())
                .build();
    }
}
