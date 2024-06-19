package samsolutions.site.tour;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
                                .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("http://localhost:8081/login")
                        .defaultSuccessUrl("http://google.com")
                        .failureUrl("http://localhost:8081/login?error")
                        .loginProcessingUrl("http://localhost:8081/login")
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
             //   .logout(logout -> logout.permitAll());
        return http.build();
    }
}