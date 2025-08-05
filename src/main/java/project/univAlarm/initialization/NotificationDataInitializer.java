package project.univAlarm.initialization;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.detector.NotificationDetector;
import project.univAlarm.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationDataInitializer {

    private final List<NotificationDetector> detectors;
    private final NotificationService notificationService;

    /**
     * 크롤링 진행 및 notification 저장
     */
    @Transactional
    public void initializeNotificationData() throws IOException {
        for (NotificationDetector detector : detectors) {
            List<CrawledNotificationDto> crawledNotificationDtos = detector.initializeDetector();
            notificationService.saveNotifications(detector, crawledNotificationDtos);
        }
    }
}
