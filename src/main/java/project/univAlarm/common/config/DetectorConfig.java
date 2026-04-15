package project.univAlarm.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.univAlarm.common.config.UnivConfigProperties.UnivConfig;
import project.univAlarm.common.config.UnivConfigProperties.UnivConfig.Campus;
import project.univAlarm.common.config.UnivConfigProperties.UnivConfig.Campus.UrlEntry;
import project.univAlarm.external.crawler.catholicUniv.Crawler;
import project.univAlarm.common.detector.NotificationDetector;

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
                    NotificationDetector detector = createNotificationDetector(entity.getUrl(), crawler);
                    detectors.add(detector);
                }
            }
        }
        return detectors;
    }

    private NotificationDetector createNotificationDetector(String url, Crawler crawler) {
        NotificationDetector detector = new NotificationDetector();
        detector.setBaseurl(url);
        detector.setCrawler(crawler);
        return detector;
    }


}
