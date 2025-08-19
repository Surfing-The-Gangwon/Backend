package tourism_data.Surfing_The_Gangwon.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tourism_data.Surfing_The_Gangwon.dto.ReservedPostResponse;
import tourism_data.Surfing_The_Gangwon.dto.WrittenPostResponse;
import tourism_data.Surfing_The_Gangwon.security.CustomUserDetails;
import tourism_data.Surfing_The_Gangwon.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/written-posts")
    public ResponseEntity<List<WrittenPostResponse>> getWrittenPost(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<WrittenPostResponse> responses = userService.getWrittenPost(userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/reserved-post")
    public ResponseEntity<List<ReservedPostResponse>> getReservedPost(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ReservedPostResponse> responses = userService.getReservedPost(userDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
