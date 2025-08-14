package tourism_data.Surfing_The_Gangwon.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.GatheringBySeashoreResponse;
import tourism_data.Surfing_The_Gangwon.dto.request.CreateGatheringRequest;
import tourism_data.Surfing_The_Gangwon.security.CustomUserDetails;
import tourism_data.Surfing_The_Gangwon.service.GatheringService;

@RestController
@RequestMapping("/api/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    public GatheringController(GatheringService gatheringService) {
        this.gatheringService = gatheringService;
    }

    @GetMapping("/{seashore_id}")
    public ResponseEntity<List<GatheringBySeashoreResponse>> getGatheringBySeashoreId(
        @RequestParam(name = "date") LocalDate date,
        @PathVariable(name = "seashore_id") Long seashoreId) {
        List<GatheringBySeashoreResponse> responses = gatheringService.getGatheringBySeashoreId(date,
            seashoreId);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping
    public ResponseEntity<Void> createGathering(@AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody CreateGatheringRequest request) {
        gatheringService.createGathering(userDetails.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/join/{gathering_id}")
    public ResponseEntity<Void> joinGathering(@AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable(name = "gathering_id") Long gatheringId) {
        gatheringService.joinGathering(userDetails.getId(), gatheringId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/cancel/{gathering_id}")
    public ResponseEntity<Void> cancelGathering(@AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable(name = "gathering_id") Long gatheringId) {
        gatheringService.cancelGathering(userDetails.getId(), gatheringId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/close/{gathering_id}")
    public ResponseEntity<Void> closeGathering(@AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable(name = "gathering_id") Long gatheringId) {
        gatheringService.closeGathering(userDetails.getId(), gatheringId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{gathering_id}")
    public ResponseEntity<Void> deleteGathering(@AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable(name = "gathering_id") Long gatheringId) {
        gatheringService.deleteGathering(userDetails.getId(), gatheringId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
