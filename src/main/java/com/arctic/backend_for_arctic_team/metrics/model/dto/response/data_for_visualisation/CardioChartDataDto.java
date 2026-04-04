package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;

import java.util.ArrayList;
import java.util.List;

public record CardioChartDataDto(
        List<String> labels,
        List<Double> heartRate,
        List<Double> stressIndex,
        List<Double> kaplanIndex
) {
    public CardioChartDataDto() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}