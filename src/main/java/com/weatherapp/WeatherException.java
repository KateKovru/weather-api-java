package com.weatherapp;

public class WeatherException extends RuntimeException{


    private final String city;

    public WeatherException(String city, String message) {
        super("Error fetching ["+city+"] "+message);
        this.city = city;
    }

    public WeatherException(String city, String message, Throwable cause) {
        super("Error fetching [" + city + "]: " + message, cause);
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
