package samsolutions.site.tour.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samsolutions.site.tour.entities.Tour;
import samsolutions.site.tour.services.TourService;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    /*@GetMapping
    public String hello() {
        tourService.createTour("Tour1");
        return "Hello World!";
    }*/

    @PostMapping
    public ResponseEntity<Tour> postTours(@RequestBody Tour tour) {
        tourService.createTour(tour);
        return new ResponseEntity<>(tour, HttpStatus.CREATED);
    }

    @GetMapping("/tours")
    public List<Tour> getTours() {
        return tourService.getTours();
    }
}
