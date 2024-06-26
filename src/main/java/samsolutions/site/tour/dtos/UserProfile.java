package samsolutions.site.tour.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserProfile {
    private String username;
    private String email;
    private String mobile;
}