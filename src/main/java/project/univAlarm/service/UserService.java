package project.univAlarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.User;
import project.univAlarm.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 사용자가 존재하지 않습니다. id="+id));
    }

    @Transactional
    public void delete(User user,Long id){
        if(!user.getId().equals(id)){
            throw new AccessDeniedException("Access denied");
        }
        userRepository.deleteById(id);
    }
}
