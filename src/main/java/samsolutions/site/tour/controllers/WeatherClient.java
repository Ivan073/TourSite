package samsolutions.site.tour.controllers;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import samsolutions.site.tour.dtos.WeatherDTO;


@FeignClient(name = "weather-client", url = "https://api.weather.yandex.ru/v2/forecast")
public interface WeatherClient {
    @GetMapping
    //@Headers("X-Yandex-API-Key: b8e2e2e0-3feb-4ecb-bc99-aed5e0c638ab")
    String getWeather( @RequestHeader("X-Yandex-API-Key") String apiKey, @RequestParam("lat") double latitude, @RequestParam("lon") double longitude);
}