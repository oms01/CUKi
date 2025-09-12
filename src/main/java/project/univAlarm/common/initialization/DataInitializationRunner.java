package project.univAlarm.common.initialization;

import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.univAlarm.common.detector.DetectorManager;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.initialization.dto.SimpleSchoolDto;
import project.univAlarm.common.utils.DateFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializationRunner implements ApplicationRunner {
    private final SchoolInitializer schoolInitializer;
    private final NotificationTypeInitializer notificationTypeInitializer;
    private final NotificationInitializer notificationInitializer;
    private final DetectorPropertiesInitializer detectorPropertiesInitializer;
    private final AdminAccountInitializer adminAccountInitializer;
    private final DetectorManager detectorManager;

    @Override
    public void run(ApplicationArguments args) throws IOException {

        Map<String, SimpleSchoolDto> schoolIds = schoolInitializer.init(); // 학교명:교정명 -> 학교정보
        log.info("[{}] School Initializing Complete", DateFormatter.currentTimeFormatted());

        Map<String, SimpleNotificationTypeDto> notificationTypeIds = notificationTypeInitializer.init(schoolIds); //url -> 공지 종류
        log.info("[{}] NotificationType Initializing Complete", DateFormatter.currentTimeFormatted());

        detectorPropertiesInitializer.init(notificationTypeIds);
        log.info("[{}] Detector Properties Initializing Complete", DateFormatter.currentTimeFormatted());

        notificationInitializer.init();
        log.info("[{}] Notification Data Initializing Complete", DateFormatter.currentTimeFormatted());

        adminAccountInitializer.init();
        log.info("[{}] Admin Account Initializing Complete", DateFormatter.currentTimeFormatted());

        log.info("[{}] DetectorManager start running", DateFormatter.currentTimeFormatted());
        detectorManager.startScheduledTask();
    }
}
