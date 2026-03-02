package com.arctic.backend_for_arctic_team.expedition.service;

import com.arctic.backend_for_arctic_team.auth.entity.User;
import com.arctic.backend_for_arctic_team.expedition.model.dto.charts.ParticipantChartsDto;

import java.util.List;
import java.util.Map;

public interface ChartsService {
    ParticipantChartsDto getParticipantCharts(String indNum, Long expeditionId, List<String> type);
    byte[] getSingleChart(String path, String indNum, Long expeditionId);
}
