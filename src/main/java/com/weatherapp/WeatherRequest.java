package com.weatherapp;

public class WeatherRequest {
    private final String city;
    private final String longitude;
    private final String latitude;


    public WeatherRequest(String city) {
        var cityKey = city.toLowerCase().trim();
        var coordinates = Config.CITY_COORDS.get(cityKey);

        if(coordinates == null){
            throw new WeatherException(cityKey,"No city found" + Config.CITY_COORDS.keySet());
        }

        this.city = cityKey;
        this.latitude = coordinates[0];
        this.longitude = coordinates[1];

    }


    public String getCity() {
        return city;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLaptitude() {
        return latitude;
    }


    public String buildURL(){
        return String.format(Config.API_BASE_URL, latitude, longitude);
    }
}
