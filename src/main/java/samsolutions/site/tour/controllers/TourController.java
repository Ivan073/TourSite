package samsolutions.site.tour.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<TourDTO> postTours(@ModelAttribute TourDTO tourdto, BindingResult result) {
        try {
            Tour entity = TourConverter.convertToEntity(tourdto);
            tourService.createTour(entity);
            return new ResponseEntity<>(TourConverter.convertToDTO(entity), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping
    public ResponseEntity<List<Tour>> getTours() {
        try {
            return new ResponseEntity<>(tourService.getTours(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable long id) {
        try {
            Optional<Tour> entity = tourService.getTourById(id);

            if (entity.isPresent()) {
                return ResponseEntity.ok(TourConverter.convertToDTO(entity.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TourDTO> deleteTourById(@PathVariable long id) {
        try {
            Optional<Tour> entity = tourService.getTourById(id);

            if (entity.isPresent()) {
                tourService.deleteTourById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping
    public ResponseEntity<TourDTO> updateTour(@RequestBody TourDTO tourdto) {
        try{
            Tour entity = TourConverter.convertToEntity(tourdto);
            int id = entity.getId();
            if (tourService.getTourById(id).isPresent()) {
                tourService.updateTour(entity);
                return new ResponseEntity<>(TourConverter.convertToDTO(entity), HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
