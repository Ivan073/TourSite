package samsolutions.site.tour.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TourDTO {
    @JsonProperty("NAME")
    private String name;
    @JsonProperty("START_DATE")
    private Date startDate;
    @JsonProperty("END_DATE")
    private Date endDate;
    @JsonProperty("COUNTRY")
    private String country;
    @JsonProperty("PRICE")
    private Double price;
    @JsonProperty("IMAGE")
    private String image;

    public TourDTO() {

    }
}
