package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;

@Builder
public record NotificationResponse(String userName, String title) {

    public static NotificationResponse create(String userName, String title) {

        return NotificationResponse.builder()
            .userName(userName)
            .title(title)
            .build();
    }
}
