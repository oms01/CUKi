package project.univAlarm.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.univAlarm.config.UnivConfigProperties.UnivConfig;
import project.univAlarm.config.UnivConfigProperties.UnivConfig.Campus;
import project.univAlarm.config.UnivConfigProperties.UnivConfig.Campus.UrlEntry;
import project.univAlarm.crawler.catholicUniv.Crawler;
import project.univAlarm.detector.NotificationDetector;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DetectorConfig {

    private final UnivConfigProperties univConfig;
    private final Map<String, Crawler> crawlers;

    @Bean
    public List<NotificationDetector> initDetectorList() {
        List<NotificationDetector> detectors = new ArrayList<>();
        for(UnivConfig config : univConfig.getUnivList()) {
            for(Campus campus : config.getCampuses()){
                Crawler crawler = crawlers.get(campus.getDefaultCrawler());
                if(crawler == null) {
                    log.info("Crawler not found : {} {} - {} ", config.getUnivName(), campus.getName(), campus.getDefaultCrawler());
                    continue;
                }
                for (UrlEntry entity : campus.getUrls()) {
                    NotificationDetector detector = createNotificationDetector(config, entity, crawler);
                    detectors.add(detector);
                }
            }
        }
        return detectors;
    }

    private NotificationDetector createNotificationDetector(UnivConfig config, UrlEntry entity, Crawler crawler) {
        NotificationDetector detector = new NotificationDetector();
        detector.setDepartment(entity.isDepartment());
        detector.setUniversityName(config.getUnivName());
        detector.setDepartmentName(entity.getName());
        detector.setBaseurl(entity.getUrl());
        detector.setCrawler(crawler);
        return detector;
    }

}
