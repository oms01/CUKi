package project.univAlarm.entity.subscription.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.entity.subscription.domain.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
}
