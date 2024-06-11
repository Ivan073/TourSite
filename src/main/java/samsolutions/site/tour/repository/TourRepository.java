package samsolutions.site.tour.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import samsolutions.site.tour.entities.Tour;

import java.util.Optional;

@Service
public interface TourRepository extends CrudRepository<Tour, Long> {

}
