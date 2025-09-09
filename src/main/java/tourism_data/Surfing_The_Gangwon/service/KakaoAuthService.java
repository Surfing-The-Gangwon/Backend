package tourism_data.Surfing_The_Gangwon.service;

import jakarta.transaction.Transactional;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.integration.KakaoApiClient;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;

@Service
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    private final RestTemplate restTemplate = new RestTemplate();

    private final WebClient webClient = WebClient.builder()
        .baseUrl("https://kapi.kakao.com")
        .build();

    private final KakaoApiClient kakaoApiClient;
    private final UserRepository userRepository;

    public KakaoAuthService(KakaoApiClient kakaoApiClient, UserRepository userRepository) {
        this.kakaoApiClient = kakaoApiClient;
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerTokens(String accessToken, String refreshToken) {
        var info = kakaoApiClient.getAccessTokenInfo(accessToken);
        String kakaoId = String.valueOf(info.id());

        User user = userRepository.findByKakaoId(kakaoId)
            .orElseGet(() -> {
                String userName = getKakaoUserName(accessToken);
                return userRepository.save(User.create(
                    kakaoId,
                    userName,
                    accessToken,
                    refreshToken
                ));
            });

        user.accessTokenUpdate(accessToken);
        if (refreshToken != null && !refreshToken.isBlank()) {
            user.refreshTokenUpdate(refreshToken);
        }

        userRepository.save(user);
    }

    private String getKakaoUserName(String accessToken) {
        try {
            var json = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

            Map<String, Object> kakaoAccount = (Map<String, Object>) json.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            return (String) profile.get("nickname");
        } catch (Exception e) {
            return "카카오사용자";
        }
    }
}
