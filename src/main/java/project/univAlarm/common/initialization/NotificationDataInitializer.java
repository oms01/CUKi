package project.univAlarm.common.initialization;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.common.detector.NotificationDetector;
import project.univAlarm.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationDataInitializer {

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
