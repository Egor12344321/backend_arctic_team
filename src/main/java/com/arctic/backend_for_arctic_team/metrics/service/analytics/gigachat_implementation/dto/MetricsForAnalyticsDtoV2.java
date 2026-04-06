package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.dto;


import lombok.Builder;

@Builder
public record MetricsForAnalyticsDtoV2(
        Double alpha,
        Double beta,
        Double theta,
        Double smr,
        Double concentration,
        Double fatigue,
        Double relax,
        Double stress,
        Double attention,
        Double cognitiveLoad,
        Double productivity
) {
}
