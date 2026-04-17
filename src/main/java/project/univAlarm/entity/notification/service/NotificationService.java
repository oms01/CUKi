package project.univAlarm.entity.notification.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.batch.initialization.dto.SimpleNotificationDto;
import project.univAlarm.entity.notification.domain.Notification;
import project.univAlarm.entity.notificationType.domain.NotificationType;
import project.univAlarm.entity.subscription.domain.Subscription;
import project.univAlarm.entity.notification.repository.NotificationRepository;
import project.univAlarm.entity.subscription.repository.SubscriptionRepository;
import project.univAlarm.entity.notification.dto.NotificationResponseDto;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final JdbcTemplate jdbcTemplate;


    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findSubscribedNotificationByUser(Long userId, int page) {
        List<Subscription> subscriptionList = subscriptionRepository.findByUserId(userId);
        List<NotificationType> notificationTypeList = subscriptionList.stream().map(Subscription::getNotificationType).toList();

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "id"));
        List<Notification> notifications = notificationRepository.findByNotificationTypeIn(notificationTypeList, pageable);
        return notifications.stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> searchNotifications(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "id"));
        List<Notification> notifications = notificationRepository.findByTitleContaining(keyword, pageable);
        return notifications.stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> searchNotificationsV2(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "id"));
        
        // Full Text Search typically works better with tokens.
        // For simplicity, we can wrap words with '+' for mandatory matching in Boolean Mode if needed,
        // but here we'll pass the keyword as is, or with simple processing.
        String processedKeyword = keyword.trim();
        if (!processedKeyword.isEmpty()) {
            // Simple logic: if multiple words, ensure they all match
            processedKeyword = "+" + processedKeyword.replace(" ", " +");
        }

        List<Notification> notifications = notificationRepository.searchByFullText(processedKeyword, pageable);
        return notifications.stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    public boolean isExist(Long notificationTypeId, Long notificationOriginId) {
        String sql = "SELECT 1 FROM notifications WHERE notification_type_id = ? AND origin_id = ? LIMIT 1";
        List<Integer> result = jdbcTemplate.queryForList(sql, Integer.class, notificationTypeId, notificationOriginId);
        return !result.isEmpty();
    }

    @Transactional
    public void saveNotifications(List<SimpleNotificationDto> simpleNotificationDtos){
        if(simpleNotificationDtos.isEmpty()) return;
        String sql = "INSERT INTO notifications (notification_type_id, origin_id, title, writer, link, date, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LocalDateTime createdAt = LocalDateTime.now();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                SimpleNotificationDto simpleNotificationType = simpleNotificationDtos.get(index);
                ps.setLong(1, simpleNotificationType.getSimpleNotificationTypeDto().getId());
                ps.setLong(2, simpleNotificationType.getOriginId());
                ps.setString(3, simpleNotificationType.getTitle());
                ps.setString(4, simpleNotificationType.getWriter());
                ps.setString(5, simpleNotificationType.getLink());
                ps.setString(6, simpleNotificationType.getDate());
                ps.setTimestamp(7, Timestamp.valueOf(createdAt));

            }
            @Override
            public int getBatchSize() {
                return simpleNotificationDtos.size();
            }
        });
    }
}
