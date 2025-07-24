package project.univAlarm.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(String username);
}
