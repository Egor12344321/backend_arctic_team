package com.arctic.backend_for_arctic_team.expedition.service;

import com.arctic.backend_for_arctic_team.expedition.model.dto.response.AnalyticsAdviceResponse;

public interface AnalyticsService {
    AnalyticsAdviceResponse getAnalyticsAdvice(String indNum, Long expeditionId);
}
