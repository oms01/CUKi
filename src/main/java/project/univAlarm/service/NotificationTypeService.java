package project.univAlarm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.NotificationType;
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
}
