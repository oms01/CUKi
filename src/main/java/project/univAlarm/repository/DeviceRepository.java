package project.univAlarm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.univAlarm.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(Long id);

    @Query("""
        SELECT ud
        FROM UserSubscription us
        JOIN us.user u
        JOIN FETCH Device ud ON ud.user = u
        WHERE us.notificationType.id = :notificationTypeId
    """)
    List<Device> findByNotificationTypeId(@Param("notificationTypeId") Long notificationTypeId);
}
