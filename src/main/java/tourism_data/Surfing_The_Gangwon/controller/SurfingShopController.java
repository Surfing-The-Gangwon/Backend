package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tourism_data.Surfing_The_Gangwon.dto.LessonDto;
import tourism_data.Surfing_The_Gangwon.dto.RentalDto;
import tourism_data.Surfing_The_Gangwon.dto.response.shop.SurfingShopInfoResponse;
import tourism_data.Surfing_The_Gangwon.dto.response.weather.BeachForecastResponse.Response;
import tourism_data.Surfing_The_Gangwon.service.SurfingShopService;

@RestController
@RequestMapping("/api/surfing")
public class SurfingShopController {

    private final SurfingShopService surfingShopService;

    public SurfingShopController(SurfingShopService surfingShopService) {
        this.surfingShopService = surfingShopService;
    }

    /**
     * 서핑샵 정보 조회
     */
    @GetMapping("/{marker_id}")
    public ResponseEntity<SurfingShopInfoResponse> getSurfingMarkerInfo(@PathVariable("marker_id") Long markerId) {
        var response = surfingShopService.getSurfingMarkerInfo(markerId);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 서핑샵 사진 등록용 API
     */
    @PostMapping("/{shop_id}")
    public ResponseEntity<Void> registerSurfingShopImg(@PathVariable("shop_id") Long shopId,
        @RequestPart(value = "shopImg", required = false) List<MultipartFile> images) {

        surfingShopService.registerSurfingShopImg(shopId, images);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 수강 항목별 수강료에 대한 정보들 반환
     */
    @GetMapping("{marker_id}/lesson")
    public ResponseEntity<List<LessonDto>> getLessonInfo(@PathVariable("marker_id") Long markerId) {
        var response = surfingShopService.getLessonInfo(markerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{marker_id}/rental")
    public ResponseEntity<List<RentalDto>> getRentalInfo(@PathVariable("marker_id") Long markerId) {
        var response = surfingShopService.getRentalInfo(markerId);
        return ResponseEntity.ok(response);
    }
}
