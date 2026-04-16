package project.univAlarm.common.initialization;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import project.univAlarm.batch.initialization.*;
import project.univAlarm.batch.initialization.dto.SimpleNotificationDto;
import project.univAlarm.batch.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.batch.initialization.dto.SimpleSchoolDto;

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
    void measureAverageExecutionTime() {
        int iterations = 100;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {

            long start = System.currentTimeMillis();

            Map<String, SimpleSchoolDto> a = schoolInitializer.init();
            Map<String, SimpleNotificationTypeDto> b = notificationTypeInitializer.init(a);
            detectorPropertiesInitializer.init(b);

            List<SimpleNotificationDto> c = detectorInitializer.init(); // 크롤링

            notificationInitializer.init(c);

            long end = System.currentTimeMillis();

            long elapsed = (end - start);
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