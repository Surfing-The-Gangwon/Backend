package tourism_data.Surfing_The_Gangwon.dto.request;

import tourism_data.Surfing_The_Gangwon.status.LEVEL;

public record CreateGatheringRequest(String title, String contents, String phone, int maxCount,
                                     String seashoreName, LEVEL level, String meetingTime) {

}
