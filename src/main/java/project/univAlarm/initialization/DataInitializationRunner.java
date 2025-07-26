package project.univAlarm.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.univAlarm.utils.DateFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializationRunner implements ApplicationRunner {
    private final UniversityDataInitializer universityDataInitializer;

    @Override
    public void run(ApplicationArguments args){
        universityDataInitializer.initializeUniversityData();
        log.info("[{}] University Data Initializing Complete", DateFormatter.currentTimeFormatted());
    }
}
