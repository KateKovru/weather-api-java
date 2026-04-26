package com.weatherapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CacheManagerTest {

    CacheManager cache;
    WeatherResponse response;

    @BeforeEach
    void setUp(){
        cache = new CacheManager(60);
        response = WeatherResponse.of("London", 18.5, 32.7, false);
    }

    @Test
    void emptyOnMiss(){
        assertTrue(cache.get("berlin").isEmpty());
    }

    @Test
    void returnsValueAfterPut(){
        cache.put("berlin", response);
        assertTrue(cache.get("berlin").isPresent());
    }

    @Test
    void caseInsensitive(){
        cache.put("BERLIN", response);
        assertTrue(cache.get("berlin").isPresent());
    }

    @Test
    void expiresAfterTTl() throws InterruptedException {
        var shortCache = new CacheManager(1);
        shortCache.put("Paris", response);
        Thread.sleep(1100);
        assertTrue(shortCache.get("paris").isEmpty());
    }

    @Test
    void evictsExpiredEntries() throws InterruptedException {
        var shortCache = new CacheManager(1);
        shortCache.put("Paris", response);
        Thread.sleep(1100);
        shortCache.evictExpired();
        assertEquals(0, shortCache.size());
    }

}
