package tourism_data.Surfing_The_Gangwon.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.status.LEVEL;
import tourism_data.Surfing_The_Gangwon.status.POST_ACTION;
import tourism_data.Surfing_The_Gangwon.status.STATE;

@Builder
public record ReservedPostResponse(Long id, String title, String contents, String phone, int currentCount,
                                  int maxCount, LocalDateTime meetingTime, LocalDate date, LEVEL level,
                                  STATE state, String city, String seashore, POST_ACTION postAction) {

    public static ReservedPostResponse create(Long id, String title, String contents, String phone,
        int currentCount, int maxCount, LocalDateTime meetingTime, LocalDate date, LEVEL level, STATE state,
        String city, String seashore, POST_ACTION postAction) {

        return ReservedPostResponse.builder()
            .id(id)
            .title(title)
            .contents(contents)
            .phone(phone)
            .currentCount(currentCount)
            .maxCount(maxCount)
            .meetingTime(meetingTime)
            .date(date)
            .level(level)
            .state(state)
            .city(city)
            .seashore(seashore)
            .postAction(postAction)
            .build();
    }
}
