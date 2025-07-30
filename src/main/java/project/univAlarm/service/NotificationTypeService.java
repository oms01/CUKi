package project.univAlarm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.School;
import project.univAlarm.repository.NotificationTypeRepository;

@Service
@RequiredArgsConstructor
public class NotificationTypeService {
    private final NotificationTypeRepository notificationTypeRepository;

    public List<NotificationType> getNotificationTypesBySchool(School school) {
        return notificationTypeRepository.findBySchool(school);
    }
}
