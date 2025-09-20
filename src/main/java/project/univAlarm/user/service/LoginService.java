package project.univAlarm.user.service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import project.univAlarm.user.domain.User;
import project.univAlarm.common.security.jwt.JWTUtil;
import project.univAlarm.user.repository.UserRepository;
import project.univAlarm.user.dto.UserJoinDto;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final RedisTemplate<String,String> redisTemplate;


    public User joinProcess(UserJoinDto joinDTO, String role) {

        Long kakaoId = joinDTO.getKakaoId();
        Optional<User> OptionalUser = userRepository.findByKakaoId(kakaoId);

        if (OptionalUser.isPresent()) {
            return OptionalUser.get();
        }

        User data = new User(joinDTO);
        data.setRole(role);

        userRepository.save(data);
        return data;
    }

    public String createToken(Long userId, String role, Long second) {
        return jwtUtil.createJwt(userId, role, second*1000L);
    }

    public String createRefreshToken(Long userId, Long second) {
        String refreshToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("refresh:" + refreshToken, String.valueOf(userId), Duration.ofSeconds(second));
        return refreshToken;
    }

    public Optional<String> validateRefreshToken(String refreshToken) {
        String userId = redisTemplate.opsForValue().get("refresh:" + refreshToken);
        return Optional.ofNullable(userId);
    }

}
