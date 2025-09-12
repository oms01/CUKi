package project.univAlarm.common.initialization;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import project.univAlarm.common.detector.DetectorManager;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.initialization.dto.SimpleSchoolDto;

@SpringBootTest
class DataInitializationRunnerTest {

    @Autowired
    private SchoolInitializer schoolInitializer;
    @Autowired
    private NotificationTypeInitializer notificationTypeInitializer;
    @Autowired
    private NotificationInitializer notificationInitializer;
    @Autowired
    private DetectorPropertiesInitializer detectorPropertiesInitializer;
    @Autowired
    private DetectorInitializer detectorInitializer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void measureAverageExecutionTime() throws Exception {
        int iterations = 100;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {

            long start = System.currentTimeMillis();

            Map<String, SimpleSchoolDto> a = schoolInitializer.init();
            Map<String, SimpleNotificationTypeDto> b = notificationTypeInitializer.init(a);
            detectorPropertiesInitializer.init(b);

            long beforeCrawling = System.currentTimeMillis();
            List<SimpleNotificationDto> c = detectorInitializer.init(); // 크롤링
            long afterCrawling = System.currentTimeMillis();

            notificationInitializer.init(c);

            long end = System.currentTimeMillis();

            long elapsed = (end - start) - (afterCrawling - beforeCrawling);
            totalTime += elapsed;

            jdbcTemplate.execute("DELETE FROM notifications");
            jdbcTemplate.execute("DELETE FROM notification_types");
            jdbcTemplate.execute("DELETE FROM schools");
            System.out.println("Iteration " + (i + 1) + " 실행 시간: " + elapsed + "ms");
        }

        double average = totalTime / (double) iterations;
        System.out.println("평균 실행 시간: " + average + "ms");
    }
}