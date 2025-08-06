package project.univAlarm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.UserSubscription;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByUserId(Long userId);
}
