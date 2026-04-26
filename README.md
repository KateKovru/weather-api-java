# WeatherAPIServer
Java CLI application that fetches weather data from the Open-Meteo API.
The project demonstrates working with HTTP requests, multithreading (ExecutorService),
and in-memory caching with TTL.

## Features
- Fetches weather data using HTTP client
- Parallel requests using ExecutorService (multithreading)
- In-memory cache with TTL (Time-To-Live)
- Error handling for API failures
- Unit tests (JUnit)

## Technologies
- Java
- Maven
- HTTP Client (java.net.http)
- Concurrency (ExecutorService)
- JUnit

## How to Run

```bash
Run Main class and pass city names as arguments:
Berlin London Paris
```

## Example Output
```bash
== First fetch (hits the API) ==
-------------------------------------------------------
[API] berlin       |  13,5°C | Wind: 10,2 km/h | 16:57:53

== Second fetch (served from cache) ==
-------------------------------------------------------
[CACHE] berlin       |  13,5°C | Wind: 10,2 km/h | 16:57:53
```

## What I Learned
- How to work with real-world APIs (Open-Meteo)
- How to implement parallel requests using ExecutorService
- How caching with TTL improves performance
- How to handle API errors and edge cases

## Project Structure
```bash
src/
 ├── main/java/com/weatherapp
 ├── test/java/com/weatherapp
```

## Contact
Feel free to reach out via GitHub!
