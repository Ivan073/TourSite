package samsolutions.site.tour.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samsolutions.site.tour.dtos.WeatherDTO;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherClient weatherClient;

    @GetMapping
    public String getWeather() {
        String apiKey = "b8e2e2e0-3feb-4ecb-bc99-aed5e0c638ab";
        return weatherClient.getWeather(apiKey,53.8992, 27.5485);
    }
}