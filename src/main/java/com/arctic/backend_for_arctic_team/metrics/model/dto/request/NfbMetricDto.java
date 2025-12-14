package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NfbMetricDto(
        @NotBlank String individualNumber,
        @NotNull Long timestamp,
        Integer session,
        Double alpha,
        Double beta,
        Double theta,
        Double delta,
        Double smr
) {
    public static NfbMetrics mapToNfbEntity(NfbMetricDto dto) {
        return NfbMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .session(dto.session())
                .alpha(dto.alpha())
                .beta(dto.beta())
                .theta(dto.theta())
                .delta(dto.delta())
                .smr(dto.smr())
                .build();
    }
}
