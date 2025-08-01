package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.SeashoreResponse;
import tourism_data.Surfing_The_Gangwon.service.SeashoreService;

@RestController
@RequestMapping("/api/seashores")
public class SeashoreController {

    private final SeashoreService seashoreService;

    public SeashoreController(SeashoreService seashoreService) {
        this.seashoreService = seashoreService;
    }

    @GetMapping("/{city_id}")
    public ResponseEntity<List<SeashoreResponse>> getSeashoresByCity(@PathVariable(name = "city_id") Long cityId) {
        List<SeashoreResponse> response = seashoreService.getSeashoresByCity(cityId);
        return ResponseEntity.ok(response);
    }

}
