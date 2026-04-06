package com.arctic.backend_for_arctic_team.metrics.service.analytics.gigachat_implementation.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "analytics.cache.type", havingValue = "redis", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class RedisAnalyticsCacheImpl implements AnalyticsCache {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String KEY_PREFIX = "analytics:";
    private static final Duration TTL = Duration.ofHours(3);

    @Override
    public Optional<String> get(String individualNumber, Long expeditionId) {
        String key = buildKey(individualNumber, expeditionId);
        String value = redisTemplate.opsForValue().get(key);

        if (value != null) {
            log.debug("Redis cache HIT for key: {}", key);
            return Optional.of(value);
        }

        log.debug("Redis cache MISS for key: {}", key);
        return Optional.empty();
    }

    @Override
    public void put(String individualNumber, Long expeditionId, String analysis) {
        String key = buildKey(individualNumber, expeditionId);
        redisTemplate.opsForValue().set(key, analysis, TTL);
        log.debug("Redis cache PUT for key: {}", key);
    }

    @Override
    public void evict(String individualNumber, Long expeditionId) {
        String key = buildKey(individualNumber, expeditionId);
        redisTemplate.delete(key);
        log.debug("Redis cache EVICT for key: {}", key);
    }

    @Override
    public void clear() {
        var keys = redisTemplate.keys(KEY_PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            log.info("Redis cache CLEAR: {} entries deleted", keys.size());
        }
    }

    private String buildKey(String individualNumber, Long expeditionId) {
        return KEY_PREFIX + individualNumber + ":" + expeditionId;
    }
}