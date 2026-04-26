import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        if(args.length == 0){
            printUsage();
            return;
        }

        var cities = Arrays.stream(args)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        var cache = new CacheManager(Config.CACHE_TTL_SECS);
        var client = new HttpWeatherClient();
        var service = new WeatherService(client, cache);

        System.out.println("\n== First fetch (hits the API) ==");
        System.out.println("-".repeat(55));
        service.fetchAll(cities).forEach(System.out::println);


        System.out.println("\n== Second fetch (served from cache) ==");
        System.out.println("-".repeat(55));
        service.fetchAll(cities).forEach(System.out::println);

        service.shutdown();


    }

    private static void printUsage(){
        System.out.println("Usage: java -jar WeatherAPIServer.jar <city1> <city2> ...");
        System.out.println("Cities: " + Config.CITY_COORDS.keySet());
    }
}
