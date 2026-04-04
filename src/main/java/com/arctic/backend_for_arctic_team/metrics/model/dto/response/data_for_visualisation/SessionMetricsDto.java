package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;

public record SessionMetricsDto(
        String date,
        String timeOfDay,
        Double alpha,
        Double beta,
        Double theta,
        Double heartRate,
        Double stressIndex,
        Double concentration,
        Double fatigue,
        Double relax
) {}