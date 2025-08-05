package project.univAlarm.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.detector.NotificationDetector;
import project.univAlarm.domain.Notification;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.UserSubscription;
import project.univAlarm.repository.NotificationRepository;
import project.univAlarm.repository.NotificationTypeRepository;
import project.univAlarm.repository.SchoolRepository;
import project.univAlarm.repository.UserSubscriptionRepository;
import project.univAlarm.service.dto.NotificationResponseDto;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SchoolRepository schoolRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final NotificationRepository notificationRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findSubscribedNotificationByUser(Long userId, int page) {
        List<UserSubscription> subscriptionList = userSubscriptionRepository.findByUserId(userId);
        List<NotificationType> notificationTypeList = subscriptionList.stream().map(UserSubscription::getNotificationType).toList();

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "createdAt"));
        List<Notification> notifications = notificationRepository.findByNotificationTypeIn(notificationTypeList, pageable);
        return notifications.stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Transactional
    public void saveNotifications(NotificationDetector detector, List<CrawledNotificationDto> crawledNotificationDtos) {
        for (CrawledNotificationDto crawledNotificationDto : crawledNotificationDtos) {
            saveNotification(detector.getNotificationTypeId(), crawledNotificationDto);
        }
    }

    public void saveNotification(Long notificationTypeId, CrawledNotificationDto crawledNotificationDto) {
        Optional<NotificationType> notificationType = notificationTypeRepository.findById(notificationTypeId);
        Optional<Notification> notification = notificationRepository.findByNotificationTypeIdAndOriginId(notificationTypeId, crawledNotificationDto.getId());
        notification.orElseGet(() -> notificationRepository.save(new Notification(notificationType.orElseThrow(), crawledNotificationDto)));
    }
}
