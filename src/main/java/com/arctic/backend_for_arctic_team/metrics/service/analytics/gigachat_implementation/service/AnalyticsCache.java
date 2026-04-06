package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;

import java.util.Optional;

public interface AnalyticsCache {

    Optional<String> get(String individualNumber, Long expeditionId);

    void put(String individualNumber, Long expeditionId, String analysis);

    void evict(String individualNumber, Long expeditionId);

    void clear();

}