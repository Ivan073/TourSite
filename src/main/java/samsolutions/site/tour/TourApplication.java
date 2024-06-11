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
	public static void main(String[] args) {
		SpringApplication.run(TourApplication.class, args);
	}
}
