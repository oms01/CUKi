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
        adminAccountInitializer.init();
        log.info("[{}] Admin Account Initializing Complete", DateFormatter.currentTimeFormatted());

    }
}
