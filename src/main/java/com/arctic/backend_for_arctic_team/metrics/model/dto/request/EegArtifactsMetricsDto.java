package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGArtifactsMetrics;

public record EegArtifactsMetricsDto(
        Integer artifactsChannel1,
        Integer artifactsChannel2,
        Integer qualityChannel1,
        Integer qualityChannel2
) {
    public static EEGArtifactsMetrics mapFromRequestToEntity(EegArtifactsMetricsDto request){
        return EEGArtifactsMetrics.builder()
                .artifactsChannel1(request.artifactsChannel1())
                .artifactsChannel2(request.artifactsChannel2())
                .qualityChannel1(request.qualityChannel1())
                .qualityChannel2(request.qualityChannel2())
                .build();
    }
}
