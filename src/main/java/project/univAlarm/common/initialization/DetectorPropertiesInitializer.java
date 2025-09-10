package project.univAlarm.common.initialization;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.common.detector.NotificationDetector;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.utils.DateFormatter;
import project.univAlarm.notificationType.domain.NotificationType;
import project.univAlarm.school.domain.School;
import project.univAlarm.notificationType.service.NotificationTypeService;
import project.univAlarm.school.dto.SchoolResponseDto;
import project.univAlarm.school.service.SchoolService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DetectorPropertiesInitializer {

    private final List<NotificationDetector> detectors;

    /**
     * detector에 notificationTypeId 저장
     */
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
