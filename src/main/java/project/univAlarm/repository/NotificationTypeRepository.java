package project.univAlarm.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.School;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    List<NotificationType> findBySchoolIdOrderByIsDepartmentAscNameAsc(Long id);
    Optional<NotificationType> findBySchoolIdAndName(Long schoolId, String name);
    List<NotificationType> findBySchool(School school);
    List<NotificationType> findByIdIn(List<Long> notificationTypeIds);

}
