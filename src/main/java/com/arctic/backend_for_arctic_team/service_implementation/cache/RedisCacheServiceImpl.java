package com.arctic.backend_for_arctic_team.service_implementation.cache;

import com.arctic.backend_for_arctic_team.service_interface.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheServiceImpl implements CacheService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REFRESH_REDIS_CACHE_PREFIX = "refresh:";

    @Override
    public void saveToCache(String individualNumber, String token) {
        log.info("Saving refreshToken to cache started for user: {}", individualNumber);
        redisTemplate.opsForValue().set(REFRESH_REDIS_CACHE_PREFIX + individualNumber, token, Duration.ofDays(7));

    }

    @Override
    public void removeFromCache(String individualNumber) {
        log.info("Removing refresh token from cache for user: {}", individualNumber);
        Boolean deleted = redisTemplate.delete(REFRESH_REDIS_CACHE_PREFIX + individualNumber);
        if (deleted){
            log.info("Cache refreshToken removed SUCCESSFULLY for user: {}", individualNumber);
        } else {
            log.info("Cache refreshToken removing FAILED for user: {}", individualNumber);
        }
    }

    @Override
    public Optional<String> getFromCache(String individualNumber) {
        log.info("Started getting refreshToken for user: {}", individualNumber);
        return Optional.ofNullable(redisTemplate.opsForValue().get(REFRESH_REDIS_CACHE_PREFIX + individualNumber));
    }
}
