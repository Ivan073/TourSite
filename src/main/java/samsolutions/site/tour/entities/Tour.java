package samsolutions.site.tour.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.awt.*;
import java.util.Date;

@Entity
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Nonnull
    private int id;

    private String name;
    private Date startDate;
    private Date endDate;
    private String country;
    private Double price;

    private String image;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    // getters, setters
}