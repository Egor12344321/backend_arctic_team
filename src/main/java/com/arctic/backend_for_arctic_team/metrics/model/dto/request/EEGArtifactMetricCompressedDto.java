package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EEGArtifactsMetricsCompressed;

public record EEGArtifactMetricCompressedDto(
        String individualNumber,
        String expeditionId,
        Long timestamp,
        Integer session,
        Boolean artifactsChannel1,
        Boolean artifactsChannel2,
        Float qualityChannel1,
        Float qualityChannel2
) {
    public static EEGArtifactsMetricsCompressed mapFromRequestToEntity(EEGArtifactMetricCompressedDto request){
        return EEGArtifactsMetricsCompressed.builder()
                .artifactsChannel1(request.artifactsChannel1())
                .artifactsChannel2(request.artifactsChannel2())
                .expeditionId(Long.valueOf(request.expeditionId))
                .qualityChannel1(request.qualityChannel1())
                .qualityChannel2(request.qualityChannel2())
                .individualNumber(request.individualNumber())
                .session(request.session())
                .timestamp(request.timestamp())
                .build();
    }
}
