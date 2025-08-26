package tourism_data.Surfing_The_Gangwon.dto.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DailyTideRequest extends BaseRequest {
    public static String BASE_DATE = "base_date";

    // 발표일자
    private String baseDate;
}
