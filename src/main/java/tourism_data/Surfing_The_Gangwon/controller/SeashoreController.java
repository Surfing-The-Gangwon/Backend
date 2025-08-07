package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDetailResponse;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyForecastResponse;
import tourism_data.Surfing_The_Gangwon.service.SeashoreService;

@RestController
@RequestMapping("/api")
public class SeashoreController {

    private final SeashoreService seashoreService;

    public SeashoreController(SeashoreService seashoreService) {
        this.seashoreService = seashoreService;
    }

    @GetMapping("/cities/{city_id}/seashores")
    public ResponseEntity<List<SeashoreResponse>> getSeashoresByCity(@PathVariable(name = "city_id") Long cityId) {
        List<SeashoreResponse> response = seashoreService.getSeashoresByCity(cityId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{seashore_id}")
    public ResponseEntity<SeashoreDetailResponse> getSeashoreById(@PathVariable(name = "seashore_id") Long seashoreId) {
        var response = seashoreService.getSeashoreById(seashoreId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{beach_code}/forecasts")
    public ResponseEntity<List<DailyForecastResponse>> getDailyForecasts(@PathVariable(name = "beach_code") Long beachCode) {
        var response = seashoreService.getDailyRangeForecast(beachCode);
        return ResponseEntity.ok(response);
    }
}
