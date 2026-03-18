package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalBaseline;

public record PhysiologicalBaselineDto(
        Long id,
        String individualNumber,
        Long timestamp,
        String expeditionId,
        Integer session,
        Double alpha,
        Double beta,
        Double alphaGravity,
        Double betaGravity,
        Double concentration
) {
    public PhysiologicalBaseline toEntity() {
        return PhysiologicalBaseline.builder()
                .id(this.id())
                .individualNumber(this.individualNumber())
                .timestamp(this.timestamp())
                .expeditionId(Long.valueOf(this.expeditionId()))
                .session(this.session())
                .alpha(this.alpha())
                .beta(this.beta())
                .alphaGravity(this.alphaGravity())
                .betaGravity(this.betaGravity())
                .concentration(this.concentration())
                .build();
    }
}