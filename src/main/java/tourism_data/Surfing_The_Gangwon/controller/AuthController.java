package tourism_data.Surfing_The_Gangwon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourism_data.Surfing_The_Gangwon.dto.request.KakaoTokenRequest;
import tourism_data.Surfing_The_Gangwon.service.KakaoAuthService;

@RestController
@RequestMapping("/auth/kakao")
public class AuthController {

    private final KakaoAuthService kakaoAuthService;

    public AuthController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody KakaoTokenRequest request) {
        kakaoAuthService.registerTokens(request.accessToken(), request.refreshToken());
        return ResponseEntity.ok().build();
    }
}
