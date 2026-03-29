package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation;

import com.arctic.backend_for_arctic_team.metrics.model.entity.CardioMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.NfbMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.PhysiologicalMetrics;
import com.arctic.backend_for_arctic_team.metrics.model.entity.ProductivityMetrics;
import lombok.Builder;

import java.util.List;

@Builder
public record MetricsForAnalyticsDto(
        List<NfbMetrics> nfbMetrics,
        List<CardioMetrics> cardioMetrics,
        List<PhysiologicalMetrics> physiologicalMetrics,
        List<ProductivityMetrics> productivityMetrics
) {

}
