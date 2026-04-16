package project.univAlarm.entity.notification.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.entity.notification.domain.Notification;
import project.univAlarm.entity.notificationType.domain.NotificationType;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByNotificationTypeIn(List<NotificationType> notificationTypes, Pageable pageable);

    boolean existsByNotificationTypeIdAndOriginId(Long notificationTypeId, Long originId);

    Optional<Notification> findByNotificationTypeId(Long notificationTypeId);

    List<Notification> findByTitleContaining(String title, Pageable pageable);
}
