package project.univAlarm.common.initialization;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.univAlarm.common.detector.DetectorManager;
import project.univAlarm.common.utils.DateFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializationRunner implements ApplicationRunner {
    private final UniversityDataInitializer universityDataInitializer;
    private final NotificationDataInitializer notificationDataInitializer;
    private final FirebaseInitializer firebaseInitializer;
    private final DetectorPropertiesInitializer detectorPropertiesInitializer;
    private final AdminAccountInitializer adminAccountInitializer;
    private final DetectorManager detectorManager;

    @Override
    public void run(ApplicationArguments args) throws IOException {

        universityDataInitializer.initializeUniversityData();
        log.info("[{}] University Data Initializing Complete", DateFormatter.currentTimeFormatted());

        detectorPropertiesInitializer.initializeDetectorProperties();
        log.info("[{}] Detector Properties Initializing Complete", DateFormatter.currentTimeFormatted());

        notificationDataInitializer.initializeNotificationData();
        log.info("[{}] Notification Data Initializing Complete", DateFormatter.currentTimeFormatted());

        firebaseInitializer.initialize();
        log.info("[{}] Firebase Initializing Complete", DateFormatter.currentTimeFormatted());

        adminAccountInitializer.initializeAdminAccount();
        log.info("[{}] Admin Account Initializing Complete", DateFormatter.currentTimeFormatted());

        log.info("[{}] DetectorManager start running", DateFormatter.currentTimeFormatted());
        detectorManager.startScheduledTask();
    }
}
