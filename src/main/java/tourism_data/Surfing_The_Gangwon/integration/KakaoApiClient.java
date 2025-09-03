package tourism_data.Surfing_The_Gangwon.integration;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoApiClient {

    private final WebClient webClient = WebClient.builder()
        .baseUrl("https://kapi.kakao.com")
        .build();

    public record TokenInfo(Long id, Long expiresIn) {

    }

    public TokenInfo getAccessTokenInfo(String accessToken) {
        return webClient.get()
            .uri("/v1/user/access_token_info")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(TokenInfo.class)
            .block();
    }

    public String getUserProfileEmail(String accessToken) {
        var json = webClient.get()
            .uri("/v2/user/me")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return json;
    }
}
