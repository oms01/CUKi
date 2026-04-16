package project.univAlarm.batch.initialization;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.univAlarm.batch.detector.DetectorManager;
import project.univAlarm.batch.initialization.dto.SimpleNotificationDto;
import project.univAlarm.batch.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.batch.initialization.dto.SimpleSchoolDto;
import project.univAlarm.common.utils.DateFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializationRunner implements ApplicationRunner {

    private final AdminAccountInitializer adminAccountInitializer;

    @Override
    public void run(ApplicationArguments args) {
//
//        Map<String, SimpleSchoolDto> schoolIds = schoolInitializer.init(); // 학교명:교정명 -> 학교정보
//        Map<String, SimpleNotificationTypeDto> notificationTypeIds = notificationTypeInitializer.init(schoolIds); //url -> 공지 종류
//        detectorPropertiesInitializer.init(notificationTypeIds);
//        log.info("[{}] Detector Properties Initializing Complete", DateFormatter.currentTimeFormatted());
//
//        long st = System.currentTimeMillis();
//        List<SimpleNotificationDto> simpleNotificationDtos = detectorInitializer.init();
//        long en = System.currentTimeMillis();
//        log.info("[{}] Detector Initializing Complete {}ms", DateFormatter.currentTimeFormatted(), en - st);
//        notificationInitializer.init(simpleNotificationDtos);
//        log.info("[{}] Notification Data Initializing Complete", DateFormatter.currentTimeFormatted());

        adminAccountInitializer.init();
        log.info("[{}] Admin Account Initializing Complete", DateFormatter.currentTimeFormatted());

//        log.info("[{}] DetectorManager start running", DateFormatter.currentTimeFormatted());
//        detectorManager.startScheduledTask();
    }
}
