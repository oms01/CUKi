package project.univAlarm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.NotificationType;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    List<NotificationType> findBySchoolIdOrderByIsDepartmentAscNameAsc(Long id);
    Optional<NotificationType> findBySchoolIdAndName(Long schoolId, String name);
    List<NotificationType> findBySchoolId(Long schoolId);
    List<NotificationType> findByIdIn(List<Long> notificationTypeIds);

}
