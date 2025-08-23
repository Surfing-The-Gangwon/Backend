package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.NotificationResponse;
import tourism_data.Surfing_The_Gangwon.security.CustomUserDetails;
import tourism_data.Surfing_The_Gangwon.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/made")
    public ResponseEntity<List<NotificationResponse>> getNotificationMade(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<NotificationResponse> responses = notificationService.getNotificationMade(
            userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/canceled")
    public ResponseEntity<List<NotificationResponse>> getNotificationCanceled(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<NotificationResponse> responses = notificationService.getNotificationCanceled(
            userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/full")
    public ResponseEntity<List<NotificationResponse>> getNotificationFull(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<NotificationResponse> responses = notificationService.getNotificationFull(
            userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
