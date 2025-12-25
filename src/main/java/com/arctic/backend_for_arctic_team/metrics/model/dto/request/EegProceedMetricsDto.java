package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGProceedMetrics;

public record EegProceedMetricsDto(
        Double channel1,
        Double channel2
) {
    public static EEGProceedMetrics mapFromRequestToEntity(EegProceedMetricsDto request){
        return EEGProceedMetrics.builder()
                .channel1(request.channel1())
                .channel2(request.channel2())
                .build();
    }
}
