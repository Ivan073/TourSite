package samsolutions.site.tour.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.util.Date;


@Data
@Entity
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private Date startDate;
    private Date endDate;
    private String country;
    private Double price;
    private String image;
}