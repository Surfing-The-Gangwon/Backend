package tourism_data.Surfing_The_Gangwon.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import tourism_data.Surfing_The_Gangwon.Constants.Format;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDetailResponse;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.dto.request.WaterTempRequest;
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
            .map((Seashore seashore) ->
                SeashoreResponse.create(seashore, getWaterTemp(seashore.getBeachCode())
                ))
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
        return response.response().body.items.item.getFirst().tw;
    }
}
