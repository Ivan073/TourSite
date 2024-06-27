package samsolutions.site.tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TourApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourApplication.class, args);
	}
}
