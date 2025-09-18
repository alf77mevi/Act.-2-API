package com.example.demo;

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
}
