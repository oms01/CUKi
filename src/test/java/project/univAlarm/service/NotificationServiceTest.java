package project.univAlarm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import project.univAlarm.entity.notification.domain.Notification;
import project.univAlarm.entity.notification.dto.NotificationResponseDto;
import project.univAlarm.entity.notification.repository.NotificationRepository;
import project.univAlarm.entity.notification.service.NotificationService;
import project.univAlarm.entity.notificationType.domain.NotificationType;
import project.univAlarm.entity.school.domain.School;
import project.univAlarm.batch.crawler.CrawledNotificationDto;
import org.springframework.test.util.ReflectionTestUtils;

import project.univAlarm.entity.subscription.domain.Subscription;
import project.univAlarm.entity.subscription.repository.SubscriptionRepository;
import project.univAlarm.entity.user.domain.User;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void findSubscribedNotificationByUserTest() {
        // Given
        Long userId = 1L;
        int page = 0;
        User user = User.builder().id(userId).build();
        School school = new School("가톨릭대학교", "성심");
        NotificationType type = new NotificationType(school, "공지", false, "http://url.com");
        Subscription subscription = new Subscription(user, type);

        when(subscriptionRepository.findByUserId(userId)).thenReturn(List.of(subscription));
        
        CrawledNotificationDto crawledDto = new CrawledNotificationDto(1L, "테스트 공지입니다.", "2024-04-16", "작성자", "http://test.com");
        Notification notification = new Notification(type, crawledDto);
        
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        when(notificationRepository.findByNotificationTypeIn(any(), pageableCaptor.capture()))
                .thenReturn(List.of(notification));

        // When
        notificationService.findSubscribedNotificationByUser(userId, page);

        // Then
        Pageable capturedPageable = pageableCaptor.getValue();
        assertThat(capturedPageable.getSort().getOrderFor("date").getDirection()).isEqualTo(org.springframework.data.domain.Sort.Direction.DESC);
        assertThat(capturedPageable.getSort().getOrderFor("id").getDirection()).isEqualTo(org.springframework.data.domain.Sort.Direction.DESC);
    }

    @Test
    void searchNotificationsTest() {
        // Given
        String keyword = "공지";
        String lastDate = null;
        Long lastId = null;
        
        School school = new School("가톨릭대학교", "성심");
        
        NotificationType type = new NotificationType(school, "공지", false, "http://url.com");
        
        CrawledNotificationDto crawledDto = new CrawledNotificationDto(1L, "테스트 공지입니다.", "2024-04-16", "작성자", "http://test.com");
        Notification notification = new Notification(type, crawledDto);
        ReflectionTestUtils.setField(notification, "id", 1L);

        when(notificationRepository.findByTitleContainingCursor(eq(keyword), eq(lastDate), eq(lastId), any(Pageable.class)))
                .thenReturn(List.of(notification));

        // When
        List<NotificationResponseDto> results = notificationService.searchNotifications(keyword, lastDate, lastId);

        // Then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getTitle()).isEqualTo("테스트 공지입니다.");
    }

    @Test
    void searchNotificationsV2Test() {
        // Given
        String keyword = "공지";
        String lastDate = null;
        Long lastId = null;
        String processedKeyword = "+공지";

        School school = new School("가톨릭대학교", "성심");
        NotificationType type = new NotificationType(school, "공지", false, "http://url.com");
        CrawledNotificationDto crawledDto = new CrawledNotificationDto(1L, "테스트 공지입니다.", "2024-04-16", "작성자", "http://test.com");
        Notification notification = new Notification(type, crawledDto);
        ReflectionTestUtils.setField(notification, "id", 1L);

        when(notificationRepository.searchByFullTextCursor(eq(processedKeyword), eq(lastDate), eq(lastId), any(Pageable.class)))
                .thenReturn(List.of(notification));

        // When
        List<NotificationResponseDto> results = notificationService.searchNotificationsV2(keyword, lastDate, lastId);

        // Then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getTitle()).isEqualTo("테스트 공지입니다.");
    }
}
