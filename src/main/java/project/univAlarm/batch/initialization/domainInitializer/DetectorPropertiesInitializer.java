package project.univAlarm.batch.initialization.domainInitializer;


import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.batch.detector.NotificationDetector;
import project.univAlarm.batch.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.utils.DateFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DetectorPropertiesInitializer {

    private final List<NotificationDetector> detectors;

    @Transactional(readOnly = true)
    public void init(Map<String, SimpleNotificationTypeDto> notificationTypeIds) {
        for (NotificationDetector detector : detectors) {

            String url = detector.getBaseurl();
            SimpleNotificationTypeDto dto = notificationTypeIds.get(url);
            if(dto == null) {
                log.info("[{}] Notification Type not found", DateFormatter.currentTimeFormatted());
                continue;
            }
            detector.setSimpleNotificationTypeDto(dto);
        }
    }
}
