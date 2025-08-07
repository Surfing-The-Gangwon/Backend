package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import lombok.Builder;

@Builder
public record DailyForecastResponse(
    String tmef, // 발효시각
    String sky, // 하늘상태
    String w2, // 풍향
    String s2, // 풍속
    String wh2, // 파고
    String wf // 예보
) {
}
