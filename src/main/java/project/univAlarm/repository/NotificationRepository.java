package project.univAlarm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByNotificationTypeIdIn(List<Long> notificationTypeIds);
    List<Notification> findByNotificationTypeId(Long notificationTypeId);
}
