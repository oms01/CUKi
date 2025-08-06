package project.univAlarm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.univAlarm.domain.UserDevice;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    List<UserDevice> findByUserId(Long id);

    @Query("""
        SELECT ud
        FROM UserSubscription us
        JOIN us.user u
        JOIN FETCH UserDevice ud ON ud.user = u
        WHERE us.notificationType.id = :notificationTypeId
    """)
    List<UserDevice> findByNotificationTypeId(@Param("notificationTypeId") Long notificationTypeId);
}
