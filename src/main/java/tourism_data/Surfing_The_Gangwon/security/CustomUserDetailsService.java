package tourism_data.Surfing_The_Gangwon.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tourism_data.Surfing_The_Gangwon.repository.UserRepository;
import tourism_data.Surfing_The_Gangwon.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accessToken) throws UsernameNotFoundException {
        User user = userRepository.findByKakaoAccessToken(accessToken)
            .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 토큰입니다."));
        return new CustomUserDetails(user);
    }
}
