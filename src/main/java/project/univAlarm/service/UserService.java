package project.univAlarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.User;
import project.univAlarm.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id="+id));
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
