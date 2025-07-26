package project.univAlarm.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.detector.NotificationDetector;
import project.univAlarm.domain.Notification;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.School;
import project.univAlarm.repository.NotificationRepository;
import project.univAlarm.repository.NotificationTypeRepository;
import project.univAlarm.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SchoolRepository schoolRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationRepository notificationRepository;

    public void saveNotifications(NotificationDetector detector, List<CrawledNotificationDto> crawledNotificationDtos) {
        for (CrawledNotificationDto crawledNotificationDto : crawledNotificationDtos) {
            saveNotification(detector, crawledNotificationDto);
        }
    }

    public void saveNotification(NotificationDetector detector, CrawledNotificationDto crawledNotificationDto) {
        Optional<School> school = schoolRepository.findByName(detector.getUniversityName());
        Optional<NotificationType> notificationType = notificationTypeRepository.findBySchoolIdAndName(
                school.orElseThrow().getId(), detector.getDepartmentName());
        Optional<Notification> notification = notificationRepository.findByNotificationTypeAndOriginId(notificationType.orElseThrow(), crawledNotificationDto.getId());
        notification.orElseGet(() -> notificationRepository.save(new Notification(notificationType.orElseThrow(), crawledNotificationDto)));
    }
}
