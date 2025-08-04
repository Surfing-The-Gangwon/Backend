package tourism_data.Surfing_The_Gangwon.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tourism_data.Surfing_The_Gangwon.Constants.Format;
import tourism_data.Surfing_The_Gangwon.Constants.Time;
import tourism_data.Surfing_The_Gangwon.dto.BeachForecast;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDetailResponse;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.request.BeachForecastRequest;
import tourism_data.Surfing_The_Gangwon.dto.request.WaterTempRequest;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.BeachForecastResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.WaterTempResponse;
import tourism_data.Surfing_The_Gangwon.entity.Seashore;
import tourism_data.Surfing_The_Gangwon.integration.WeatherClient;
import tourism_data.Surfing_The_Gangwon.repository.SeashoreRepository;

@Service
public class SeashoreService {
    private final SeashoreRepository seashoreRepository;
    private final WeatherClient weatherClient;

    public SeashoreService(SeashoreRepository seashoreRepository, WeatherClient weatherClient) {
        this.seashoreRepository = seashoreRepository;
        this.weatherClient = weatherClient;
    }

    public List<SeashoreResponse> getSeashoresByCity(Long cityId) {
        return seashoreRepository.findByCityId(cityId)
            .stream()
            .map((Seashore seashore) -> {
                BeachForecastResponse forecastResponse = getBeachForecast(seashore.getBeachCode());
                return SeashoreResponse.create(seashore, getWaterTemp(seashore.getBeachCode()),
                    BeachForecast.create(forecastResponse)
                );
            })
            .toList();
    }

    public SeashoreDetailResponse getSeashoreById(Long seashoreId) {
        Seashore seashoreEntity = seashoreRepository.findById(seashoreId)
            .orElseThrow(() -> new RuntimeException("seashore not found"));
        return SeashoreDetailResponse.create(seashoreEntity);
    }

    private String getWaterTemp(Integer beachCode) {
        var searchTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        WaterTempRequest request = WaterTempRequest.builder()
            .beachNum(String.valueOf(beachCode))
            .searchTime(searchTime)
            .build();

        WaterTempResponse response = weatherClient.getWeaterTemp(request);
        var items = response.response().body.items.item;
        return items.isEmpty() ? "" : items.getFirst().tw;
    }

    private BeachForecastResponse getBeachForecast(Integer beachCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validTimes = getValidBaseDateTime(now);
        var dateTime = validTimes.format(DateTimeFormatter.ofPattern(Format.DATE_FORMAT_ONE_LINE));
        var baseDate = dateTime.substring(0, 8);
        var baseTime = dateTime.substring(8);

        BeachForecastRequest request = BeachForecastRequest.builder()
            .numOfRows(String.valueOf(20))
            .baseDate(baseDate)
            .baseTime(baseTime)
            .beachNum(String.valueOf(beachCode))
            .build();

        return weatherClient.getBeachForecast(request);
    }

    private LocalDateTime getValidBaseDateTime(LocalDateTime currTime) {
        int currHour = currTime.getHour();
        int[] validTimes = Time.validTimes;

        // 현재 시간보다 이전의 가장 가까운 유효 시간 탐색
        for (int i = validTimes.length - 1; i >= 0; i--) {
            if (currHour >= validTimes[i]) {
                return currTime.withHour(validTimes[i]).withMinute(0).withSecond(0).withNano(0);
            }
        }

        // 현재 시간이 02시보다 이전이면, 전날의 23시 사용 (오전 12시 이후, 익일 02시 이전일 경우)
        return currTime.minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }
}
