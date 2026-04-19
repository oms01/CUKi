package project.univAlarm.entity.notification.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.univAlarm.entity.notification.domain.Notification;
import project.univAlarm.entity.notificationType.domain.NotificationType;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n " +
            "WHERE n.notificationType IN :notificationTypes " +
            "AND (:lastDate IS NULL OR n.date < :lastDate OR (n.date = :lastDate AND n.id < :lastId)) " +
            "ORDER BY n.date DESC, n.id DESC")
    List<Notification> findByNotificationTypeInCursor(
            @Param("notificationTypes") List<NotificationType> notificationTypes,
            @Param("lastDate") String lastDate,
            @Param("lastId") Long lastId,
            Pageable pageable);

    boolean existsByNotificationTypeIdAndOriginId(Long notificationTypeId, Long originId);

    Optional<Notification> findByNotificationTypeId(Long notificationTypeId);

    @Query("SELECT n FROM Notification n " +
            "WHERE n.title LIKE %:title% " +
            "AND (:lastDate IS NULL OR n.date < :lastDate OR (n.date = :lastDate AND n.id < :lastId)) " +
            "ORDER BY n.date DESC, n.id DESC")
    List<Notification> findByTitleContainingCursor(
            @Param("title") String title,
            @Param("lastDate") String lastDate,
            @Param("lastId") Long lastId,
            Pageable pageable);

    @Query(value = "SELECT * FROM notifications " +
            "WHERE MATCH(title) AGAINST(:keyword IN BOOLEAN MODE) " +
            "AND (:lastDate IS NULL OR date < :lastDate OR (date = :lastDate AND id < :lastId)) " +
            "ORDER BY date DESC, id DESC",
            nativeQuery = true)
    List<Notification> searchByFullTextCursor(
            @Param("keyword") String keyword,
            @Param("lastDate") String lastDate,
            @Param("lastId") Long lastId,
            Pageable pageable);
}
