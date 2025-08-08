package tourism_data.Surfing_The_Gangwon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;
import tourism_data.Surfing_The_Gangwon.Constants.URL.WEATHER;
import tourism_data.Surfing_The_Gangwon.security.filter.KakaoTokenAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final KakaoTokenAuthenticationFilter kakaoTokenAuthenticationFilter;

    public SecurityConfig(KakaoTokenAuthenticationFilter kakaoTokenAuthenticationFilter) {
        this.kakaoTokenAuthenticationFilter = kakaoTokenAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/gathering/**").authenticated()
                .anyRequest().permitAll()
            )
            .addFilterBefore(kakaoTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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