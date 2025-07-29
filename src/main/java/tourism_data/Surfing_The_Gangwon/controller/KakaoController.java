package tourism_data.Surfing_The_Gangwon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tourism_data.Surfing_The_Gangwon.service.KakaoService;

@Controller
public class KakaoController {

    private final KakaoService kakaoService;

    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/oauth/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code, Model model) {
        String accessToken = kakaoService.getAccessToken(code);

        System.out.println("Access Token: " + accessToken);

        model.addAttribute("accessToken", accessToken);
        return "accessToken";
    }

    @GetMapping("/kakao")
    public String loginPage() {
        return "login";
    }
}
