package project.univAlarm.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.domain.Notification;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.UserSubscription;
import project.univAlarm.repository.NotificationRepository;
import project.univAlarm.repository.UserSubscriptionRepository;
import project.univAlarm.service.dto.NotificationResponseDto;

@Service
@RequiredArgsConstructor
public class NotificationService {
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
    public List<Notification> saveNotifications(NotificationType notificationType, List<CrawledNotificationDto> crawledNotificationDtos) {
        List<Notification> savedNotifications =  new ArrayList<>();
        for (CrawledNotificationDto crawledNotificationDto : crawledNotificationDtos) {
            Notification notification = saveNotification(notificationType, crawledNotificationDto);
            if(notification != null) savedNotifications.add(notification);
        }
        return savedNotifications;
    }

    public Notification saveNotification(NotificationType notificationType, CrawledNotificationDto crawledNotificationDto) {
        boolean notiExists = notificationRepository.existsByNotificationTypeIdAndOriginId(notificationType.getId(), crawledNotificationDto.getId());
        if(!notiExists) {
            return notificationRepository.save(new Notification(notificationType, crawledNotificationDto));
        }
        return null;
    }
}
