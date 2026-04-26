package com.weatherapp;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class CacheManager {

    private record Entry(WeatherResponse data, Instant expiredTime){}
    private final Map<String, Entry> store= new HashMap<>();
    private final long ttlSeconds;


    public CacheManager(long ttlSeconds) {
        this.ttlSeconds = ttlSeconds;
    }

    public void put(String city, WeatherResponse response){

        store.put(key(city),new Entry(response, Instant.now().plusSeconds(ttlSeconds)));

    }

    public Optional<WeatherResponse> get (String city){
        var entry = store.get(key(city));
        if(entry == null || isExpired(entry)){
            store.remove(key(city));
            return Optional.empty();
        }
        return Optional.of(entry.data);
    }

    public void evictExpired(){
        store.entrySet().removeIf(e -> isExpired(e.getValue()));
    }

    public int size(){
        return store.size();
    }

    public void clear(){
        store.clear();
    }

    private boolean isExpired(Entry entry){
        return Instant.now().isAfter(entry.expiredTime);
    }

    private String key(String city) {
        return city.toLowerCase().trim();
    }

}
