package project.univAlarm.detector;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.service.NotificationService;
import project.univAlarm.utils.DateFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class DetectorManager {

    private final List<NotificationDetector> detectors;
    private final NotificationService notificationService;

    @Scheduled(fixedRate = 60 * 1000)
    public void run() throws IOException {
        long start = System.currentTimeMillis();
        for (NotificationDetector detector : detectors) {
            List<CrawledNotificationDto> crawledNotificationDtos = detector.runDetector();
            notificationService.saveNotifications(detector.getNotificationType(), crawledNotificationDtos);
        }
        long end = System.currentTimeMillis();
        log.info("[{}] {} Change Detector finished in {} ms", DateFormatter.currentTimeFormatted(), detectors.size(), end - start);
    }
}
