package project.univAlarm.common.initialization;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.univAlarm.common.detector.NotificationDetector;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.initialization.dto.SimpleSchoolDto;

@SpringBootTest
class DetectorInitializerTest {

    @Autowired
    private DetectorInitializer detectorInitializer;

    @Test
    void 기존_순차_크롤링() throws IOException {
        int iterations = 100;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {

            long start = System.currentTimeMillis();
            List<SimpleNotificationDto> c = detectorInitializer.initv1();
            long end = System.currentTimeMillis();

            long elapsed = end - start;
            totalTime += elapsed;

            System.out.println("Iteration " + (i + 1) + " 실행 시간: " + elapsed + "ms");
        }

        double average = totalTime / (double) iterations;
        System.out.println("평균 실행 시간: " + average + "ms");
    }

    @Test
    void 병렬_크롤링() throws IOException {
        int iterations = 100;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {

            long start = System.currentTimeMillis();
            List<SimpleNotificationDto> c = detectorInitializer.init();
            long end = System.currentTimeMillis();

            long elapsed = end - start;
            totalTime += elapsed;

            System.out.println("Iteration " + (i + 1) + " 실행 시간: " + elapsed + "ms");
        }

        double average = totalTime / (double) iterations;
        System.out.println("평균 실행 시간: " + average + "ms");
    }
}