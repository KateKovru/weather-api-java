import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record WeatherResponse(
        String city,
        double temperature,
        double windSpeed,
        boolean fromCache,
        String fetchedAt
) {

    public static WeatherResponse of(String city, double temp, double wind, boolean cached){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return new WeatherResponse(city,temp, wind, cached,time);
    }

    @Override
    public String toString() {

        String source = fromCache ? "[CACHE]" : "[API]";

        return String.format("%s %-12s | %5.1f°C | Wind: %4.1f km/h | %s", source, city, temperature, windSpeed, fetchedAt);
    }
}
