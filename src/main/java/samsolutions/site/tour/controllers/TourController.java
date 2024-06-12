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
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable long id) {
        Optional<Tour> entity = tourService.getTourById(id);

        if (entity.isPresent()) {
            return ResponseEntity.ok(TourConverter.convertToDTO(entity.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TourDTO> deleteTourById(@PathVariable long id) {
        Optional<Tour> entity = tourService.getTourById(id);

        if (entity.isPresent()) {
            tourService.deleteTourById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
