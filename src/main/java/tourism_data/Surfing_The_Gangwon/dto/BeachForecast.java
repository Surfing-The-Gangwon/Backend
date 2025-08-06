package tourism_data.Surfing_The_Gangwon.dto;

import lombok.Builder;
import tourism_data.Surfing_The_Gangwon.Constants.ForecastCategory;
import tourism_data.Surfing_The_Gangwon.Constants.Unit;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.BeachForecastResponse;
import tourism_data.Surfing_The_Gangwon.util.Range;

import java.util.Map;

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
            .map(item -> item.fcstValue + Unit.CELSIUS)
            .findFirst()
            .orElse("");

        String wh = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.WAV))
            .map(item -> item.fcstValue + Unit.METER)
            .findFirst()
            .orElse("");

        String ws = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.WSD))
            .map(item -> item.fcstValue + Unit.MS)
            .findFirst()
            .orElse("");

        String wd = items.stream()
            .filter(item -> item.category.equals(ForecastCategory.VEC))
            .map(item -> getWindDirCategory(item.fcstValue))
            .findFirst()
            .orElse("");

        return BeachForecast.builder()
            .temp(tmp)
            .waveHeight(wh)
            .windSpeed(ws)
            .windDir(wd)
            .build();
    }

    private static String getWindDirCategory(String windDir) {
        var windDirNum = Integer.parseInt(windDir);
        
        var windCategories = Map.of(
            Range.of(0, 44), "N", // 북
            Range.of(45, 89), "NE", // 북동
            Range.of(90, 134), "E", // 동
            Range.of(135, 179), "SE", // 남동
            Range.of(180, 224), "S", // 남
            Range.of(225, 269), "SW", // 남서
            Range.of(270, 314), "W", // 서
            Range.of(315, 360), "NW" // 북서
        );

        return windCategories.entrySet().stream()
            .filter(entry -> entry.getKey().contains(windDirNum))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse("");
    }
}
