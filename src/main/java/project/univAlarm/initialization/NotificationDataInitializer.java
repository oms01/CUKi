package project.univAlarm.initialization;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.detector.NotificationDetector;
import project.univAlarm.service.NotificationService;
import project.univAlarm.utils.DateFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationDataInitializer {

    private final List<NotificationDetector> detectors;
    private final NotificationService notificationService;

    @Transactional
    public void initializeNotificationData() throws IOException {
        for (NotificationDetector detector : detectors) {
            List<CrawledNotificationDto> crawledNotificationDtos = detector.initializeDetector();
            notificationService.saveNotifications(detector, crawledNotificationDtos);
        }
        log.info("[{}] {} Detector initialized", DateFormatter.currentTimeFormatted(), detectors.size());
    }
}
