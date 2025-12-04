package com.arctic.backend_for_arctic_team.service_implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.arctic.backend_for_arctic_team.service_implementation.AuthServiceImpl.ACCESS_PREFIX;


@RequiredArgsConstructor
@Service
@Slf4j
public class TokenBlackListedService {
    private final RedisTemplate<String, String> redisTemplate;

    public String generateTokenId(String token) {
        return DigestUtils.sha256Hex(token);
    }

    public boolean isAccessTokenBlacklisted(String token) {

        try {
            String tokenId = generateTokenId(token);
            String key = ACCESS_PREFIX + tokenId;
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("Error checking token blacklist", e);
            return false;
        }
    }
}
