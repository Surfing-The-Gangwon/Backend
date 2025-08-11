package tourism_data.Surfing_The_Gangwon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import tourism_data.Surfing_The_Gangwon.Constants.URL;
import tourism_data.Surfing_The_Gangwon.Constants.URL.WEATHER;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable());
        return http.build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(WEATHER.BASE_URL)
                .build();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
