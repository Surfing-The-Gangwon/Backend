package tourism_data.Surfing_The_Gangwon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tourism_data.Surfing_The_Gangwon.dto.AccessTokenResponse;
import tourism_data.Surfing_The_Gangwon.dto.KakaoTokenResponse;
import tourism_data.Surfing_The_Gangwon.dto.KakaoUserInfo;
import tourism_data.Surfing_The_Gangwon.entity.User;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;

@Service
public class KakaoLoginService {
    private final UserRepository userRepository;
    private final KakaoAuthService kakaoAuthService;

    public KakaoLoginService(UserRepository userRepository, KakaoAuthService kakaoAuthService) {
        this.userRepository = userRepository;
        this.kakaoAuthService = kakaoAuthService;
    }

    @Transactional
    public KakaoTokenResponse kakaoLoginAndGetTokens(String code) {
        KakaoTokenResponse kakaoToken = kakaoAuthService.getTokensByCode(code);
        KakaoUserInfo kakaoUser = kakaoAuthService.getUserInfo(kakaoToken.access_token());

        User user = userRepository.findByKakaoId(kakaoUser.id())
            .orElseGet(() -> User.create(kakaoUser.id(), kakaoUser.userName(), kakaoToken.access_token(),
                    kakaoToken.refresh_token()));

        user.accessTokenUpdate(kakaoToken.access_token());
        user.refreshTokenUpdate(kakaoToken.refresh_token());
        userRepository.save(user);

        return KakaoTokenResponse.create(user.getKakaoAccessToken(), user.getKakaoRefreshToken());
    }

    @Transactional
    public AccessTokenResponse reissueAccessToken(String refreshToken) {
        AccessTokenResponse response = kakaoAuthService.getAccessTokenByRefreshToken(refreshToken);
        User user = userRepository.findByKakaoRefreshToken(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.accessTokenUpdate(response.access_token());

        return response;
    }
}
