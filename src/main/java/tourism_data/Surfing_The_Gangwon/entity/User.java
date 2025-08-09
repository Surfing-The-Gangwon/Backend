package tourism_data.Surfing_The_Gangwon.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_TB")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false)
    private String kakaoId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "kakao_access_token", nullable = false)
    private String kakaoAccessToken;

    @Column(name = "kakao_refresh_token", nullable = false)
    private String kakaoRefreshToken;

    protected User() {
    }

    @Builder
    private User(String kakaoId, String userName, String kakaoAccessToken, String kakaoRefreshToken) {
        this.kakaoId = kakaoId;
        this.userName = userName;
        this.kakaoAccessToken = kakaoAccessToken;
        this.kakaoRefreshToken = kakaoRefreshToken;
    }

    public static User create(String kakaoId, String userName, String kakaoAccessToken,
        String kakaoRefreshToken) {
        return User.builder()
            .kakaoId(kakaoId)
            .userName(userName)
            .kakaoAccessToken(kakaoAccessToken)
            .kakaoRefreshToken(kakaoRefreshToken)
            .build();
    }

    public void accessTokenUpdate(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public void refreshTokenUpdate(String kakaoRefreshToken) {
        this.kakaoRefreshToken = kakaoRefreshToken;
    }
}
