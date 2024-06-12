package samsolutions.site.tour.converters;

import jakarta.servlet.ServletContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import samsolutions.site.tour.dtos.TourDTO;
import samsolutions.site.tour.entities.Tour;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
        String dirpath = System.getProperty("user.dir");
        String filePath = dirpath+"\\images\\"+ UUID.randomUUID().toString()+".png";
        File serverFile = new File(filePath);
        try {
            tourDTO.getImage().transferTo(serverFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tour.setImage(filePath);
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

        //путь к файлу не конвертируется обратно в файл (изображения возвращаются как отдельный запрос)
        return tourDTO;
    }
}
