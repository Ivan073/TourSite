package samsolutions.site.tour.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
public class LoginRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
