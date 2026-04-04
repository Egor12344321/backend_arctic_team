package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;

import java.util.ArrayList;
import java.util.List;

public record PhysioChartDataDto(
        List<String> labels,
        List<Double> relax,
        List<Double> fatigue,
        List<Double> concentration,
        List<Double> stress,
        List<Double> involvement
) {
    public PhysioChartDataDto() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}