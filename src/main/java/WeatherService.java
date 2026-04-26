import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class WeatherService {

    private final HttpWeatherClient client;
    private final CacheManager cache;
    private final ExecutorService executor;

    public WeatherService(HttpWeatherClient client, CacheManager cache) {
        this.client = client;
        this.cache = cache;
        this.executor = Executors.newFixedThreadPool(Config.THREAD_COUNT);
    }

    public List<WeatherResponse> fetchAll(List<String>cities){

        var tasks = cities.stream().map(city -> (Callable<WeatherResponse>)()
                    -> fetchOne(city)).collect(Collectors.toList());

        try {
            var futures = executor.invokeAll(tasks);

            return futures.stream().map(f-> {
                try {
                    return f.get();

                } catch (Exception e) {
                    System.out.println("Failed: "+e.getMessage());
                    return null;
                }
            }).filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Fetch interrupted", e);
        }

    }

    private WeatherResponse fetchOne(String city){
        var cached = cache.get(city);
        if(cached.isPresent()){
            var r = cached.get();
            return new WeatherResponse(r.city(),r.temperature(),r.windSpeed(), true, r.fetchedAt());
        }

        var request = new WeatherRequest(city);
        var response = client.fetch(request);
        cache.put(city, response);

        return response;
    }

    public void shutdown(){
        executor.shutdown();
    }

}
