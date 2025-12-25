package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGRawMetrics;

public record EegRawMetricsDto(
        Double channel1,
        Double channel2
) {
    public static EEGRawMetrics mapFromRequestToEntity(EegRawMetricsDto request){
        return EEGRawMetrics.builder()
                .channel1(request.channel1())
                .channel2(request.channel2())
                .build();
    }
}
