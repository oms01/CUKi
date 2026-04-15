package project.univAlarm.common.detector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.external.crawler.catholicUniv.Crawler;

@Slf4j
@Getter @Setter
@NoArgsConstructor
public class NotificationDetector {

    private String baseurl;
    private Crawler crawler;
    private SimpleNotificationTypeDto simpleNotificationTypeDto;

    private List<CrawledNotificationDto> notificationList = new ArrayList<>();

    public List<CrawledNotificationDto> initializeDetector() throws IOException {
        try{
            notificationList = crawler.crawl(baseurl);
        } catch (Exception e){
            System.out.println("Crawling failed" + baseurl);
        }
        return notificationList;
    }

    /**
     * 새로 감지된 공지사항 return
     *  + notificationList 갱신
     */
    public List<CrawledNotificationDto> runDetector() throws IOException {
        try {
            List<CrawledNotificationDto> crawledNotificationDtos = crawler.crawl(baseurl);
            List<CrawledNotificationDto> detectedNotifications = new ArrayList<>();
            crawledNotificationDtos.stream()
                    .filter(crawledNotificationDto -> !notificationList.contains(crawledNotificationDto))
                    .forEach(detectedNotifications::add);

            notificationList = crawledNotificationDtos;
            return detectedNotifications;
        } catch (Exception e) {
            System.out.println(baseurl);
        }
        return new ArrayList<CrawledNotificationDto>();
    }
}
