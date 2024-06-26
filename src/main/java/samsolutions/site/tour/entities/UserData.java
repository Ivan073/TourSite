package samsolutions.site.tour.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.util.Date;


@Data
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String email;
    private String role;
    private String mobile;
}