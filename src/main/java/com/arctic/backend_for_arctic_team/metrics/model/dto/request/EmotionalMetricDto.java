package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.EmotionalMetrics;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmotionalMetricDto(
        @NotBlank String individualNumber,
        @NotNull Long timestamp,
        Integer session,
        Double attention,
        Double relaxation,
        Double cognitiveLoad,
        Double cognitiveControl,
        Double selfControl
) {
    public static EmotionalMetrics mapToEmotionalEntity(EmotionalMetricDto dto) {
        return EmotionalMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .session(dto.session())
                .attention(dto.attention())
                .relaxation(dto.relaxation())
                .cognitiveLoad(dto.cognitiveLoad())
                .cognitiveControl(dto.cognitiveControl())
                .selfControl(dto.selfControl())
                .build();
    }
}
