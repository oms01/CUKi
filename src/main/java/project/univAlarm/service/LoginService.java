package project.univAlarm.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.User;
import project.univAlarm.jwt.JWTUtil;
import project.univAlarm.repository.UserRepository;
import project.univAlarm.service.dto.UserJoinDto;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    public User joinProcess(UserJoinDto joinDTO) {

        Long kakoId = joinDTO.getKakaoId();

        Optional<User> OptionalUser = userRepository.findByKakaoId(kakoId);

        if (OptionalUser.isPresent()) {
            return OptionalUser.get();
        }

        User data = new User(joinDTO);
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
        return data;
    }

    public String createToken(User userEntity) {
        return jwtUtil.createJwt(userEntity.getId(), userEntity.getRole(), 60*60*10L);
    }

}
