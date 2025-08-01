package project.univAlarm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.Notification;
import project.univAlarm.domain.NotificationType;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByNotificationTypeIn(List<NotificationType> notificationTypes);
    List<Notification> findByNotificationTypeId(Long notificationTypeId);
    Optional<Notification> findByNotificationTypeAndOriginId(NotificationType nOtificationType, Long originId);
}
