package tourism_data.Surfing_The_Gangwon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tourism_data.Surfing_The_Gangwon.dto.KakaoTokenResponse;
import java.util.Map;
import tourism_data.Surfing_The_Gangwon.dto.KakaoUserInfo;

@Service
public class KakaoAuthService {

    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    private final RestTemplate restTemplate = new RestTemplate();

    public KakaoTokenResponse getTokensByCode(String code) {
        String url = "https://kauth.kakao.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(params, headers);
        ResponseEntity<KakaoTokenResponse> resp = restTemplate.exchange(url, HttpMethod.POST, req,
            KakaoTokenResponse.class);

        return resp.getBody();
    }

    public KakaoUserInfo getUserInfo(String kakaoAccessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(kakaoAccessToken);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> resp = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = resp.getBody();
        String id = String.valueOf(body.get("id"));
        String userName = ((Map) body.get("properties")).get("nickname").toString();

        return new KakaoUserInfo(id, userName);
    }
}
