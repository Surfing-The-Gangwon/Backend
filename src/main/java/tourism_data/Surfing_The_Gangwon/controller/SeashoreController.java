package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.CityDto;
import tourism_data.Surfing_The_Gangwon.dto.MarkerInfo;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDetailResponse;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreDto;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyForecastResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.DailyTideFilteredResponse;
import tourism_data.Surfing_The_Gangwon.entity.City;
import tourism_data.Surfing_The_Gangwon.service.SeashoreService;

@RestController
@RequestMapping("/api")
public class SeashoreController {

    private final SeashoreService seashoreService;

    public SeashoreController(SeashoreService seashoreService) {
        this.seashoreService = seashoreService;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> response = seashoreService.getAllCities();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities/{city_id}/seashores")
    public ResponseEntity<List<SeashoreResponse>> getSeashoresByCity(@PathVariable(name = "city_id") Long cityId) {
        List<SeashoreResponse> response = seashoreService.getSeashoresByCity(cityId);
        return ResponseEntity.ok(response);
    }

    /**
     * 기상 정보 없이 도시별 해안 기본 정보 리스트만 반환하는 엔드포인트
     */
    @GetMapping("/cities/{city_id}/seashores/basic")
    public ResponseEntity<List<SeashoreDto>> getBasicSeashoresByCity(@PathVariable(name = "city_id") Long cityId) {
        List<SeashoreDto> response = seashoreService.getBasicSeashoresByCity(cityId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{seashore_id}")
    public ResponseEntity<SeashoreDetailResponse> getSeashoreById(@PathVariable(name = "seashore_id") Long seashoreId) {
        var response = seashoreService.getSeashoreById(seashoreId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{beach_code}/forecasts")
    public ResponseEntity<List<DailyForecastResponse>> getDailySeaForecasts(@PathVariable(name = "beach_code") Long beachCode) {
        var response = seashoreService.getDailySeaForecast(beachCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{beach_code}/forecasts/tide")
    public ResponseEntity<List<DailyTideFilteredResponse>> getDailyTideForecasts(@PathVariable(name = "beach_code") Long beachCode) {
        var response = seashoreService.getDailyTideForecast(beachCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{seashore_id}/markers")
    public ResponseEntity<List<MarkerInfo>> getMarkersBySeashore(@PathVariable(name = "seashore_id") Long seashoreId) {
        var response = seashoreService.getMarkersBySeashore(seashoreId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seashores/{seashore_id}/uv")
    public ResponseEntity<String> getUv(@PathVariable(name = "seashore_id") Long seashoreId) {
        var response = seashoreService.getUVForecast(seashoreId);
        return ResponseEntity.ok(response);
    }
}
