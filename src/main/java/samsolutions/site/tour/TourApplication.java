package samsolutions.site.tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import samsolutions.site.tour.entities.Tour;
import samsolutions.site.tour.services.TourService;

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
}
