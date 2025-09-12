package project.univAlarm.common.initialization;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataInitializationRunnerTest {

    @Autowired
    private DataInitializationRunner dataInitializationRunner;

    @Autowired
    private ApplicationArguments applicationArguments;

    @Test
    void measureAverageExecutionTime() throws Exception {
        int iterations = 100;
        long totalTime = 0;

        for (int i = 0; i < iterations; i++) {
            long start = System.currentTimeMillis();
            dataInitializationRunner.run(applicationArguments);
            long end = System.currentTimeMillis();

            long elapsed = end - start;
            totalTime += elapsed;

            System.out.println("Iteration " + (i + 1) + " 실행 시간: " + elapsed + "ms");
        }

        double average = totalTime / (double) iterations;
        System.out.println("평균 실행 시간: " + average + "ms");
    }
}