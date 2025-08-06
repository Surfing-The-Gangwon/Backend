package tourism_data.Surfing_The_Gangwon.dto.response.weather;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.util.WavePeriodParser;

@Builder
public record WavePeriodResponse(
    String wp
) {
    public static WavePeriodResponse create(String json) {
        return WavePeriodResponse.builder()
            .wp(WavePeriodParser.parseWeatherData(json))
            .build();
    }
}
