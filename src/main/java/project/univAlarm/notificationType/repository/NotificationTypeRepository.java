package project.univAlarm.notificationType.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.notificationType.domain.NotificationType;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    List<NotificationType> findBySchoolIdOrderByIsDepartmentAscNameAsc(Long schoolId);
    Optional<NotificationType> findBySchoolIdAndName(Long schoolId, String name);
}
