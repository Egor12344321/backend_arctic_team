package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;

public record AllChartsDataDto(
        NfbChartDataDto nfb,
        CardioChartDataDto cardio,
        PhysioChartDataDto physio,
        ProductivityChartDataDto productivity
) {}