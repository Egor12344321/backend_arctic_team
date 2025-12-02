package com.arctic.backend_for_arctic_team.service_interface;

public interface CacheService {
    void saveToCache(String individualNumber, String token);
    void removeFromCache(String individualNumber);
    void getFromCache(String individualNumber);
}
