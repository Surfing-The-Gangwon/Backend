package tourism_data.Surfing_The_Gangwon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourism_data.Surfing_The_Gangwon.dto.KakaoTokenResponse;
import tourism_data.Surfing_The_Gangwon.service.KakaoLoginService;

@RestController
@RequestMapping("/oauth")
public class KakaoController {
    private final KakaoLoginService kakaoLoginService;

    public KakaoController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<KakaoTokenResponse> kakaoCallback(@RequestParam("code") String code) {
        KakaoTokenResponse response = kakaoLoginService.kakaoLoginAndGetTokens(code);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
