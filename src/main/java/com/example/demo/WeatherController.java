package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final String WEATHER_API  = "https://api.openweathermap.org/data/2.5/weather";
    private final String API_KEY      = "5a7876a58dcdee34f7cfd242e8126b33";
    private final String GEO_API      = "http://api.openweathermap.org/geo/1.0/direct";

    @GetMapping()
    public String getMethodName(@RequestParam String latitude, @RequestParam String longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String url = WEATHER_API + "?lat=" + latitude + "&lon=" + longitude 
            + "&units=metric" + "&appid=" + API_KEY;
        String result = restTemplate.getForObject(url, String.class);
        if (result != null) {
            return result;
        }    
        return "Error: Unable to fetch weather data.";
    }   

    @GetMapping("/by-city")
    public String getWeatherByCity(@RequestParam String city) {
        RestTemplate restTemplate = new RestTemplate();

        String geoUrl = GEO_API + "?q=" + city + "&limit=1&appid=" + API_KEY;
        GeoLocation[] results = restTemplate.getForObject(geoUrl, GeoLocation[].class);

        if (results == null || results.length == 0) {
            return "Error: No se encontró la ciudad especificada.";
        }

        double lat = results[0].getLat();
        double lon = results[0].getLon();

        String weatherUrl = WEATHER_API + "?lat=" + lat + "&lon=" + lon
                + "&units=metric&appid=" + API_KEY;
        String weatherJson = restTemplate.getForObject(weatherUrl, String.class);

        if (weatherJson == null) {
            return "Error: No se pudo obtener el clima.";
        }
        return weatherJson;
    }

}
