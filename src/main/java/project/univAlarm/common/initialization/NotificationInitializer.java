package project.univAlarm.common.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.common.detector.NotificationDetector;
import project.univAlarm.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationInitializer {

    private final List<NotificationDetector> detectors;
    private final NotificationService notificationService;

    /**
     * 크롤링 진행 및 notification 저장
     */
    public void init() throws IOException {
        List<SimpleNotificationDto> simpleNotificationDtos = new ArrayList<>();
        for (NotificationDetector detector : detectors) {
            List<CrawledNotificationDto> crawledNotificationDtos = detector.initializeDetector();
            for(CrawledNotificationDto crawledNotificationDto : crawledNotificationDtos) {
                simpleNotificationDtos.add(new SimpleNotificationDto(detector.getSimpleNotificationTypeDto(), crawledNotificationDto));
            }
        }
        notificationService.saveNotifications(simpleNotificationDtos);
    }


}
