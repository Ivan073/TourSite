package samsolutions.site.tour;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/tours").hasRole("USER")
                                .requestMatchers("/tours/images/**").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("http://localhost:8081/login") // URL сервера аутентификации
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }
}