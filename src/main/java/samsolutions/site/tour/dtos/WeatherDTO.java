package samsolutions.site.tour.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {
    @JsonProperty("temp")
    private int temp;
    @JsonProperty("feel")
    private int feel;
}