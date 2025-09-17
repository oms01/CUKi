package project.univAlarm.notification.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.notification.domain.Notification;
import project.univAlarm.notificationType.domain.NotificationType;
import project.univAlarm.subscription.domain.Subscription;
import project.univAlarm.notification.repository.NotificationRepository;
import project.univAlarm.subscription.repository.SubscriptionRepository;
import project.univAlarm.notification.dto.NotificationResponseDto;

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

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "date"));
        List<Notification> notifications = notificationRepository.findByNotificationTypeIn(notificationTypeList, pageable);
        return notifications.stream()
                .map(NotificationResponseDto::new)
                .toList();
    }

    public boolean isExist(Long notificationTypeId, Long notificationOriginId) {
        String sql = "SELECT notification_type_id FROM notifications WHERE notification_type_id = ? AND origin_id = ?";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, notificationOriginId, notificationTypeId);
        return id != null;
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
