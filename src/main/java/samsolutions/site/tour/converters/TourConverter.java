package samsolutions.site.tour.converters;

import org.springframework.beans.BeanUtils;
import samsolutions.site.tour.dtos.TourDTO;
import samsolutions.site.tour.entities.Tour;

public class TourConverter {
    public static Tour convertToEntity(TourDTO tourDTO) {
        Tour tour = new Tour();
        tour.setId(tourDTO.getId());
        tour.setCountry(tourDTO.getCountry());
        tour.setName(tourDTO.getName());
        tour.setEndDate(tourDTO.getEndDate());
        tour.setStartDate(tourDTO.getStartDate());
        tour.setPrice(tourDTO.getPrice());
        // image у dto будет файлом, а у entity строкой с путем к файлу
        return tour;
    }

    public static TourDTO convertToDTO(Tour tour) {
        TourDTO tourDTO = new TourDTO();
        BeanUtils.copyProperties(tour, tourDTO);
        tourDTO.setId(tour.getId());
        tourDTO.setCountry(tour.getCountry());
        tourDTO.setName(tour.getName());
        tourDTO.setEndDate(tour.getEndDate());
        tourDTO.setStartDate(tour.getStartDate());
        tourDTO.setPrice(tour.getPrice());
        // image у dto будет файлом, а у entity строкой с путем к файлу
        return tourDTO;
    }
}
