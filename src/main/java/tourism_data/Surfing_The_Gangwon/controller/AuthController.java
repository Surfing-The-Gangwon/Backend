package tourism_data.Surfing_The_Gangwon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourism_data.Surfing_The_Gangwon.dto.AccessTokenResponse;
import tourism_data.Surfing_The_Gangwon.dto.KakaoTokenResponse;
import tourism_data.Surfing_The_Gangwon.service.KakaoLoginService;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    private final KakaoLoginService kakaoLoginService;

    public AuthController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<KakaoTokenResponse> kakaoCallback(@RequestParam("code") String code) {
        KakaoTokenResponse response = kakaoLoginService.kakaoLoginAndGetTokens(code);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/kakao/refresh")
    public ResponseEntity<AccessTokenResponse> refreshAccessToken(
        @RequestParam("refreshToken") String refreshToken) {
        AccessTokenResponse response = kakaoLoginService.reissueAccessToken(refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
