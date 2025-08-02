package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.GuidelineResponse;
import tourism_data.Surfing_The_Gangwon.service.GuidelineService;

@RestController
@RequestMapping("/api/guidelines")
public class GuideController {

    private final GuidelineService guidelineService;

    public GuideController(GuidelineService guidelineService) {
        this.guidelineService = guidelineService;
    }

    @GetMapping
    public ResponseEntity<List<GuidelineResponse>> getGuidelinesByType(@RequestParam String type) {
        var response = guidelineService.getGuidelinesByType(type);
        return ResponseEntity.ok(response);
    }
}
