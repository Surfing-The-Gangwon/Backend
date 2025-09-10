package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;

@Builder
public record UserNameResponse(String userName) {

    public static UserNameResponse create(String userName) {

        return UserNameResponse.builder()
            .userName(userName)
            .build();
    }
}
