package tourism_data.Surfing_The_Gangwon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
