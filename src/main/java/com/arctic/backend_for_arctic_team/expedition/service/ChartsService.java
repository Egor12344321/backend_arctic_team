package com.arctic.backend_for_arctic_team.expedition.service;

import com.arctic.backend_for_arctic_team.auth.entity.User;

import java.util.Map;

public interface ChartsService {
    Map<String, Object> getUserCharts(String individualNumber, Long expeditionId, User user);
    Map<String, Object> getParticipantCharts(Long participantId, Long expeditionId, User currentUser);
}
