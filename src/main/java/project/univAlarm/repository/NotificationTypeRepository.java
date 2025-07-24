package project.univAlarm.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.univAlarm.domain.NotificationType;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {
    List<NotificationType> findBySchoolIdOrderByIsDepartmentAscNameAsc(Long id);
}
