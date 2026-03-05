package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EmotionalMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.EmotionalMetricsCompressed;

public record EmotionalMetricCompressedDto(
        String individualNumber,
        String expeditionId,
        Long timestamp,
        Integer session,
        Double attention,
        Double relaxation,
        Double cognitiveLoad,
        Double cognitiveControl,
        Double selfControl
) {
    public static EmotionalMetricsCompressed mapToEmotionalEntity(EmotionalMetricCompressedDto dto) {
        return EmotionalMetricsCompressed.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(dto.expeditionId)
                .session(dto.session())
                .attention(dto.attention())
                .relaxation(dto.relaxation())
                .cognitiveLoad(dto.cognitiveLoad())
                .cognitiveControl(dto.cognitiveControl())
                .selfControl(dto.selfControl())
                .build();
    }
}
