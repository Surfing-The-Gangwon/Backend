package tourism_data.Surfing_The_Gangwon.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.status.LEVEL;
import tourism_data.Surfing_The_Gangwon.status.STATE;

@Builder
public record GatheringBySeashoreResponse(Long gatheringId, String title, String contents, String phone,
                                          int currentCount, int maxCount, LocalDateTime meetingTime,
                                          LocalDate date, LEVEL level, STATE state) {

    public static GatheringBySeashoreResponse create(Long gatheringId, String title, String contents,
        String phone, int currentCount, int maxCount, LocalDateTime meetingTime, LocalDate date,
        LEVEL level, STATE state) {

        return GatheringBySeashoreResponse.builder()
            .gatheringId(gatheringId)
            .title(title)
            .contents(contents)
            .phone(phone)
            .currentCount(currentCount)
            .maxCount(maxCount)
            .meetingTime(meetingTime)
            .date(date)
            .level(level)
            .state(state)
            .build();
    }
}
