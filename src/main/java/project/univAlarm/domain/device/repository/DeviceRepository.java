package project.univAlarm.domain.device.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.univAlarm.domain.device.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(Long id);

    @Query("""
    SELECT d
    FROM Device d
    JOIN d.user u
    JOIN Subscription us ON us.user = u
    WHERE us.notificationType.id = :notificationTypeId
""")
    List<Device> findDevicesByNotificationTypeId(@Param("notificationTypeId") Long notificationTypeId);
}
