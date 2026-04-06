package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class RateLimiterService {

    private final AnalyticsCache analyticsCache;

    private static final String RATE_LIMIT_PREFIX = "ratelimit:3hours:";

    private static final long BLOCK_HOURS = 3;

    public boolean isAllowed(String individualNumber, Long expeditionId) {

        String key = RATE_LIMIT_PREFIX + individualNumber + ":" + expeditionId;

        var cached = analyticsCache.get(individualNumber, expeditionId);

        if (cached.isPresent()) {
            log.warn("Rate limit exceeded for user: {}, expedition: {}", individualNumber, expeditionId);
            return false;
        }

        return true;
    }

    public void blockNextRequests(String individualNumber, Long expeditionId) {
        String blockedUntil = String.valueOf(Instant.now().plusSeconds(BLOCK_HOURS * 3600).toEpochMilli());
        analyticsCache.put(individualNumber, expeditionId, blockedUntil);
        log.info("Rate limit BLOCKED for user: {}, expedition: {} for 3 hours", individualNumber, expeditionId);
    }


    public String getDeadline(String individualNumber, Long expeditionId){
        return analyticsCache.get(individualNumber, expeditionId).get();
    }
}