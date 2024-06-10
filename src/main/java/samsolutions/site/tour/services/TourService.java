package samsolutions.site.tour.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import samsolutions.site.tour.entities.Tour;
import samsolutions.site.tour.repository.TourRepository;

import java.sql.*;

@Service
public class TourService {
    @Autowired
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String name) {
        Tour tour = new Tour();
        tour.setName(name);
        return tourRepository.save(tour);
    }
}