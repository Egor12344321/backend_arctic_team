package com.arctic.backend_for_arctic_team.metrics.service.charts;

import com.arctic.backend_for_arctic_team.expedition.model.dto.charts.ParticipantChartsDto;

import java.util.List;

public interface ChartsService {
    ParticipantChartsDto getParticipantCharts(String indNum, Long expeditionId, List<String> type);
    byte[] getSingleChart(String path, String indNum, Long expeditionId);
}
