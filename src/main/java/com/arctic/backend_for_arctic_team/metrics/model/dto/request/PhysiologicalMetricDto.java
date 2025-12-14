package com.arctic.backend_for_arctic_team.metrics.model.dto.request;

import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalMetrics;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PhysiologicalMetricDto(
        @NotBlank String individualNumber,
        @NotNull Long timestamp,
        Integer session,
        Double relax,
        Double fatigue,
        Double none,
        Double concentration,
        Double involvement,
        Double stress,
        Integer nfbArtifacts,
        Integer cardioArtifacts
) {
    public static PhysiologicalMetrics mapToPhysiologicalEntity(PhysiologicalMetricDto dto) {
        return PhysiologicalMetrics.builder()
                .individualNumber(dto.individualNumber())
                .timestamp(dto.timestamp())
                .session(dto.session())
                .relax(dto.relax())
                .fatigue(dto.fatigue())
                .none(dto.none())
                .concentration(dto.concentration())
                .involvement(dto.involvement())
                .stress(dto.stress())
                .nfbArtifacts(dto.nfbArtifacts())
                .cardioArtifacts(dto.cardioArtifacts())
                .build();
    }
}
