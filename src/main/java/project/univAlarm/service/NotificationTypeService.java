package project.univAlarm.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.School;
import project.univAlarm.repository.NotificationTypeRepository;
import project.univAlarm.service.dto.NotificationTypeResponseDto;

@Service
@RequiredArgsConstructor
public class NotificationTypeService {
    private final NotificationTypeRepository notificationTypeRepository;

    @Transactional(readOnly = true)
    public List<NotificationTypeResponseDto> findBySchool(Long school) {
        List<NotificationType> notificationTypes = notificationTypeRepository.findBySchoolId(school);
        return notificationTypes.stream()
                .map(NotificationTypeResponseDto::new)
                .toList();
    }

    public Optional<NotificationType> findBySchoolIdAndCampus(Long schoolId, String campus) {
        return notificationTypeRepository.findBySchoolIdAndName(schoolId, campus);
    }

    public Optional<NotificationType> findBySchoolAndDepartment(School school, String departmentName) {
        return notificationTypeRepository.findBySchoolIdAndName(school.getId(), departmentName);
    }
}
