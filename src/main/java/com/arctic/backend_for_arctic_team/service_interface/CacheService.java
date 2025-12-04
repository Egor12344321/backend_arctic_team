package com.arctic.backend_for_arctic_team.service_interface;

import java.util.Optional;

public interface CacheService {
    void saveToCache(String prefix, String token);
    void removeFromCache(String prefix);
    Optional<String> getFromCache(String prefix);
}
