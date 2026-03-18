package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetricsCompressed;
import com.arctic.backend_for_arctic_team.metrics.repository.NfbMetricsCompressedRepository;

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
    public static NfbMetricsCompressed mapToNfbEntity(NfbMetricCompressedDto dto) {
        return NfbMetricsCompressed.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .expeditionId(Long.valueOf(dto.expeditionId))
                .session(dto.session())
                .alpha(dto.alpha())
                .beta(dto.beta())
                .theta(dto.theta())
                .delta(dto.delta())
                .smr(dto.smr())
                .build();
    }
}
