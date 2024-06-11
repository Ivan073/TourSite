package samsolutions.site.tour.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samsolutions.site.tour.converters.TourConverter;
import samsolutions.site.tour.dtos.TourDTO;
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
    public ResponseEntity<TourDTO> postTours(@RequestBody TourDTO tourdto) {
        Tour entity = TourConverter.convertToEntity(tourdto);
        tourService.createTour(entity);
        return new ResponseEntity<>(TourConverter.convertToDTO(entity), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Tour> getTours() {
        return tourService.getTours();
    }
}
