package samsolutions.site.tour.controllers;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samsolutions.site.tour.converters.TourConverter;
import samsolutions.site.tour.dtos.TourDTO;
import samsolutions.site.tour.entities.Tour;
import samsolutions.site.tour.services.SolrService;
import samsolutions.site.tour.services.TourService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    private TourService tourService;

    @Autowired
    private SolrService solrService;

    @PostMapping
    public ResponseEntity<TourDTO> postTours(@RequestParam(value = "NAME", required = false) String name,
                                             @RequestParam(value = "IMAGE", required = false) MultipartFile image,
                                             @RequestParam(value = "START_DATE", required = false) String startDateStr,
                                             @RequestParam(value = "END_DATE", required = false) String endDateStr,
                                             @RequestParam(value = "COUNTRY", required = false) String country,
                                             @RequestParam(value = "PRICE", required = false) Double price) {
        try {
            TourDTO tourDTO = new TourDTO();
            tourDTO.setName(name);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if(startDateStr != null) {
                Date startDate = dateFormat.parse(startDateStr);
                tourDTO.setStartDate(startDate);
            }

            if(endDateStr != null) {
                Date endDate = dateFormat.parse(endDateStr);
                tourDTO.setEndDate(endDate);
            }

            tourDTO.setCountry(country);
            tourDTO.setPrice(price);
            tourDTO.setImage(image);

            Tour entity = TourConverter.convertToEntity(tourDTO);
            tourService.createTour(entity);
            return new ResponseEntity<>(TourConverter.convertToDTO(entity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping
    public ResponseEntity<List<TourDTO>> getTours() {
        try {
            return new ResponseEntity<List<TourDTO>>(
                    (List<TourDTO>) tourService.getTours().stream().map(TourConverter::convertToDTO).collect(Collectors.toList()),
                    HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<TourDTO>> getToursByCountry(@PathVariable("country") String country) throws SolrServerException, IOException {
       try {
            return new ResponseEntity<List<TourDTO>>(
                    solrService.getByCountry(country).
                            stream().
                            map(TourConverter::convertToDTO).
                            collect(Collectors.toList()),
                    HttpStatus.OK);
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

    @GetMapping("/images/{id}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getTourImageById(@PathVariable long id) {
        try {
            Optional<Tour> entity = tourService.getTourById(id);
            if (entity.isPresent()) {
                String path = entity.get().getImage();
                if (path == null){
                    return ResponseEntity.notFound().build();
                }
                MediaType contentType = MediaType.IMAGE_PNG;
                File file = new File(path);
                FileInputStream in = new FileInputStream(file);
                return ResponseEntity.ok()
                        .contentType(contentType)
                        .body(new InputStreamResource(in));
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
    public ResponseEntity<TourDTO> updateTour(@RequestParam(value = "ID", required = false) int id,
                                              @RequestParam(value = "NAME", required = false) String name,
                                              @RequestParam(value = "IMAGE", required = false) MultipartFile image,
                                              @RequestParam(value = "START_DATE", required = false) String startDateStr,
                                              @RequestParam(value = "END_DATE", required = false) String endDateStr,
                                              @RequestParam(value = "COUNTRY", required = false) String country,
                                              @RequestParam(value = "PRICE", required = false) Double price) {
        try{
            TourDTO tourDTO = new TourDTO();
            tourDTO.setName(name);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if(startDateStr != null) {
                Date startDate = dateFormat.parse(startDateStr);
                tourDTO.setStartDate(startDate);
            }

            if(endDateStr != null) {
                Date endDate = dateFormat.parse(endDateStr);
                tourDTO.setEndDate(endDate);
            }

            tourDTO.setCountry(country);
            tourDTO.setPrice(price);
            tourDTO.setImage(image);

            Tour entity = TourConverter.convertToEntity(tourDTO);
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
