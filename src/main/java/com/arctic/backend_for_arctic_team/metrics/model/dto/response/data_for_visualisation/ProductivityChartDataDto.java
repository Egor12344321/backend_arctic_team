package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;

import java.util.ArrayList;
import java.util.List;

public record ProductivityChartDataDto(
        List<String> labels,
        List<Double> gravity,
        List<Double> productivity,
        List<Double> fatigue,
        List<Double> concentration,
        List<Double> relaxation
) {
    public ProductivityChartDataDto() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}