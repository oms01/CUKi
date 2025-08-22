package project.univAlarm.user.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

    public User joinProcess(UserJoinDto joinDTO, String role) {

        Long kakoId = joinDTO.getKakaoId();

        Optional<User> OptionalUser = userRepository.findByKakaoId(kakoId);

        if (OptionalUser.isPresent()) {
            return OptionalUser.get();
        }

        User data = new User(joinDTO);
        data.setRole(role);

        userRepository.save(data);
        return data;
    }

    public String createToken(User userEntity, Long second) {
        return jwtUtil.createJwt(userEntity.getId(), userEntity.getRole(), second*1000L);
    }

}
