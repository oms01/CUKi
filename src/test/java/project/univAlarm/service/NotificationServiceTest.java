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

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void searchNotificationsTest() {
        // Given
        String keyword = "공지";
        int page = 0;
        
        School school = new School("가톨릭대학교", "성심");
        
        NotificationType type = new NotificationType(school, "공지", false, "http://url.com");
        
        CrawledNotificationDto crawledDto = new CrawledNotificationDto(1L, "테스트 공지입니다.", "2024-04-16", "작성자", "http://test.com");
        Notification notification = new Notification(type, crawledDto);
        ReflectionTestUtils.setField(notification, "id", 1L);

        when(notificationRepository.findByTitleContaining(eq(keyword), any(Pageable.class)))
                .thenReturn(List.of(notification));

        // When
        List<NotificationResponseDto> results = notificationService.searchNotifications(keyword, page);

        // Then
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getTitle()).isEqualTo("테스트 공지입니다.");
    }
}
