package com.arctic.backend_for_arctic_team.expedition.service;

import com.arctic.backend_for_arctic_team.auth.entity.User;

import java.util.Map;

public interface ChartsService {
    Map<String, Object> getParticipantCharts(String indNum, Long expeditionId, String type);

    Map<String, Map<String, Object>> getAllExpeditionCharts(Long expeditionId);
}
