package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;

@Slf4j
@Builder
public record SeashoreResponse(
    Long id,
    String name,
    String temp, // 온도
    String waterTemp, // 수온
    String waveHeight, // 파고
    String windSpeed, //
    String windDir, // 풍향
    String wavePeriod // 파주기 (파도가 오는 간격)
) {

    public static SeashoreResponse create(Seashore seashore, String waterTemp, BeachForecast forecast,
        String wavePeriod) {
        return SeashoreResponse.builder()
            .id(seashore.getId())
            .name(seashore.getName())
            .temp(forecast.temp())
            .waveHeight(forecast.waveHeight())
            .windSpeed(forecast.windSpeed())
            .windDir(forecast.windDir())
            .waterTemp(waterTemp)
            .wavePeriod(wavePeriod)
            .build();
    }
}
