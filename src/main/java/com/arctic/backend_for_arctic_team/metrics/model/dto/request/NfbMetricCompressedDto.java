package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;

public record NfbMetricCompressedDto(
        String individualNumber,
        String expeditionId,
        Long timestamp,
        Integer session,
        Double alpha,
        Double beta,
        Double theta,
        Double delta,
        Double smr
) {
    public static NfbMetrics mapToNfbEntity(NfbMetricCompressedDto dto) {
        return NfbMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(dto.expeditionId)
                .session(dto.session())
                .alpha(dto.alpha())
                .beta(dto.beta())
                .theta(dto.theta())
                .delta(dto.delta())
                .smr(dto.smr())
                .build();
    }
}
