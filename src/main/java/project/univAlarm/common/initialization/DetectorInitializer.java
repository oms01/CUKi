package project.univAlarm.common.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.univAlarm.common.detector.NotificationDetector;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.external.crawler.CrawledNotificationDto;

@Component
@RequiredArgsConstructor
public class DetectorInitializer {

    private final List<NotificationDetector> detectors;

    public List<SimpleNotificationDto> init() throws IOException {
        List<SimpleNotificationDto> simpleNotificationDtos = new ArrayList<>();
        for (NotificationDetector detector : detectors) {
            List<CrawledNotificationDto> crawledNotificationDtos = detector.initializeDetector();
            for(CrawledNotificationDto crawledNotificationDto : crawledNotificationDtos) {
                simpleNotificationDtos.add(new SimpleNotificationDto(detector.getSimpleNotificationTypeDto(), crawledNotificationDto));
            }
        }
        return simpleNotificationDtos;
    }
}
