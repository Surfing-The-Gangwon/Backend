package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;

@Builder
public record AccessTokenResponse(String access_token) {

    public static AccessTokenResponse create(String access_token) {

        return AccessTokenResponse.builder()
            .access_token(access_token)
            .build();
    }
}
