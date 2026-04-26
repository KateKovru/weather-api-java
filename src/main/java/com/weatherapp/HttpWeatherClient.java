package com.weatherapp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpWeatherClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final Pattern TEMP_PATTERN = Pattern.compile("\"temperature\"\\s*:\\s*([-\\d.]+)");
    private final Pattern WIND_PATTERN = Pattern.compile("\"windspeed\"\\s*:\\s*([-\\d.]+)");


    public WeatherResponse fetch(WeatherRequest request){

        try {
        var httpRequest = HttpRequest.newBuilder().uri(URI.create(request.buildURL()))
                            .GET().build();

        var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode()!=200){
            throw new WeatherException(request.getCity(), "HTTP"+response.statusCode());
        }

        return parse(request.getCity(), response.body());


        } catch (WeatherException e) {
            throw e;
        } catch (Exception e) {
            throw new WeatherException(request.getCity(),  "Network error: " + e.getMessage(), e);
        }
    }

    private WeatherResponse parse(String city, String json){

        double temp = extract(TEMP_PATTERN, json, city);
        double wind = extract(WIND_PATTERN, json, city);

        return WeatherResponse.of(city, temp, wind, false);
    }

    private double extract (Pattern pattern, String json, String city){

        Matcher m = pattern.matcher(json);
        if(m.find()) return Double.parseDouble(m.group(1));
        throw new WeatherException(city, "Could not parse API response");
    }

}
