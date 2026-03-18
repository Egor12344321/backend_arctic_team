package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityIndex;

public record ProductivityIndexDto(
        String expeditionId,
        String individualNumber,
        Long timestamp,
        Integer session,
        String relaxation,
        String stress,
        Double gravityBaseline,
        Double productivityBaseline,
        Double fatigueBaseline,
        Double reverseFatigueBaseline,
        Double relaxationBaseline,
        Double concentrationBaseline,
        Boolean hasArtifacts
) {
    public ProductivityIndex toEntity() {
        return ProductivityIndex.builder()
                .expeditionId(Long.valueOf(this.expeditionId()))
                .individualNumber(this.individualNumber())
                .timestamp(this.timestamp())
                .session(this.session())
                .relaxation(this.relaxation())
                .stress(this.stress())
                .gravityBaseline(this.gravityBaseline())
                .productivityBaseline(this.productivityBaseline())
                .fatigueBaseline(this.fatigueBaseline())
                .reverseFatigueBaseline(this.reverseFatigueBaseline())
                .relaxationBaseline(this.relaxationBaseline())
                .concentrationBaseline(this.concentrationBaseline())
                .hasArtifacts(this.hasArtifacts())
                .build();
    }
}