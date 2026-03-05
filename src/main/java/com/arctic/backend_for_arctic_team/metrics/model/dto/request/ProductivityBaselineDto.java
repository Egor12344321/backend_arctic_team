package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityBaseline;

public record ProductivityBaselineDto(
        String expeditionId,
        String individualNumber,
        Long timestamp,
        Integer session,
        Double gravity,
        Double productivity,
        Double fatigue,
        Double reverseFatigue,
        Double relaxation,
        Double concentration
) {
    public ProductivityBaseline toEntity() {
        return ProductivityBaseline.builder()
                .expeditionId(this.expeditionId())
                .individualNumber(this.individualNumber())
                .timestamp(this.timestamp())
                .session(this.session())
                .gravity(this.gravity())
                .productivity(this.productivity())
                .fatigue(this.fatigue())
                .reverseFatigue(this.reverseFatigue())
                .relaxation(this.relaxation())
                .concentration(this.concentration())
                .build();
    }
}