package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.Constants.ForecastCategory;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.BeachForecastResponse;

@Builder
public record BeachForecast(
    String temp, // 기온
    String waveHeight, // 파고
    String windSpeed, // 풍속
    String windDir // 풍향
) {
    public static BeachForecast create(BeachForecastResponse response) {
        var items = response.response().body.items.item;

        String tmp = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.TMP))
            .map(item -> item.fcstValue)
            .findFirst()
            .orElse("");

        String wh = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.WAV))
            .map(item -> item.fcstValue)
            .findFirst()
            .orElse("");

        String ws = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.WSD))
            .map(item -> item.fcstValue)
            .findFirst()
            .orElse("");

        String wd = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.VEC))
            .map(item -> item.fcstValue)
            .findFirst()
            .orElse("");

        return BeachForecast.builder()
            .temp(tmp)
            .waveHeight(wh)
            .windSpeed(ws)
            .windDir(wd)
            .build();
    }
}
