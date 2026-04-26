package com.weatherapp;

public class Config {

    public static final int    THREAD_COUNT   = 4;
    public static final long   CACHE_TTL_SECS = 60;
    public static final String API_BASE_URL   =
            "https://api.open-meteo.com/v1/forecast" +
                    "?latitude=%s&longitude=%s&current_weather=true";

    public static final java.util.Map<String, String[]> CITY_COORDS =
            java.util.Map.of(
                    "london",   new String[]{"51.5085", "-0.1257"},
                    "berlin",   new String[]{"52.5244", "13.4105"},
                    "new york", new String[]{"40.7143", "-74.0060"},
                    "tokyo",    new String[]{"35.6895", "139.6917"},
                    "paris",    new String[]{"48.8534", "2.3488"},
                    "sydney",   new String[]{"33.8678", "151.2073"},
                    "rome",     new String[]{"41.8947", "12.4839"},
                    "madrid",   new String[]{"40.4165", "-3.7026"}
            );

    private Config(){}
}
