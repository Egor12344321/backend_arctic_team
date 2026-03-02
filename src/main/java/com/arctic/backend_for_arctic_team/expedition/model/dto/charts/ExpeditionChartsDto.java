package com.arctic.backend_for_arctic_team.expedition.model.dto.charts;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ExpeditionChartsDto {
    private Long expeditionId;
    private Map<String, List<ChartDto>> participantsCharts;
}