package com.arctic.backend_for_arctic_team.metrics.model.dto.response.data_for_visualisation;

import java.util.ArrayList;
import java.util.List;

public record NfbChartDataDto(
        List<String> labels,
        List<Double> alpha,
        List<Double> beta,
        List<Double> theta,
        List<Double> delta,
        List<Double> smr
) {
    public NfbChartDataDto() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}