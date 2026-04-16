package project.univAlarm.entity.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.entity.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(Long kakaoId);
}
