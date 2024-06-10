package samsolutions.site.tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samsolutions.site.tour.entities.Tour;
import samsolutions.site.tour.services.TourService;

import java.util.List;

@SpringBootApplication
@RestController
public class TourApplication {
	@Autowired
	private TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(TourApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		tourService.createTour("Tour1");
		return "Hello World!";
	}

	@PostMapping("/tours")
	public ResponseEntity<Tour> post_tours(@RequestBody Tour tour) {
		tourService.createTour(tour);
		return new ResponseEntity<>(tour, HttpStatus.CREATED);
	}

	@GetMapping("/tours")
	public List<Tour> get_tours() {
		return tourService.getTours();
	}
}
