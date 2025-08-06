package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Builder
public record SeashoreResponse(
    Long id,
    String name,
    String temp, // 온도
    String waterTemp, // 수온
    String waveHeight, // 파고
    String windSpeed, //
    String windDir // 풍향
) {

    public static SeashoreResponse create(Seashore seashore, String waterTemp, BeachForecast forecast,
        String wavePeriod) {
        // TODO
        return SeashoreResponse.builder()
            .id(seashore.getId())
            .name(seashore.getName())
            .temp(forecast.temp())
            .waveHeight(forecast.waveHeight())
            .windSpeed(forecast.windSpeed())
            .windDir(forecast.windDir())
            .waterTemp(waterTemp)
            .build();
    }
}
