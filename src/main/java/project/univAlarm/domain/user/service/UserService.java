package project.univAlarm.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.user.domain.User;
import project.univAlarm.domain.user.repository.UserRepository;
import project.univAlarm.domain.user.dto.UserResponseDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id=" + id));
        return new UserResponseDto(user);
    }

    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
