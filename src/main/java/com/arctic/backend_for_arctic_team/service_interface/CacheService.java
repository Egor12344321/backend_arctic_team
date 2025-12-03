package com.arctic.backend_for_arctic_team.service_interface;

import java.util.Optional;

public interface CacheService {
    void saveToCache(String individualNumber, String token);
    void removeFromCache(String individualNumber);
    Optional<String> getFromCache(String individualNumber);
}
