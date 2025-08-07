package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;

@Builder
public record KakaoTokenResponse(String access_token, String refresh_token) {

    public static KakaoTokenResponse create(String access_token, String refresh_token) {

        return KakaoTokenResponse.builder()
            .access_token(access_token)
            .refresh_token(refresh_token)
            .build();
    }
}
