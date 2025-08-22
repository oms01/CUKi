package project.univAlarm.common.detector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import project.univAlarm.common.crawler.CrawledNotificationDto;
import project.univAlarm.common.crawler.catholicUniv.Crawler;
import project.univAlarm.notificationType.domain.NotificationType;
import project.univAlarm.school.domain.School;

@Slf4j
@Getter @Setter
@NoArgsConstructor
public class NotificationDetector {

    private String baseurl;
    private Crawler crawler;
    private NotificationType notificationType;
    private School school;

    private String universityName;
    private String campusName;
    private String departmentName;
    private boolean department;
    private List<CrawledNotificationDto> notificationList = new ArrayList<>();

    public List<CrawledNotificationDto> initializeDetector() throws IOException {
        notificationList = crawler.crawl(baseurl);
        return notificationList;
    }

    /**
     * 새로 감지된 공지사항 return
     *  + notificationList 갱신
     */
    public List<CrawledNotificationDto> runDetector() throws IOException {
        List<CrawledNotificationDto> crawledNotificationDtos = crawler.crawl(baseurl);

        List<CrawledNotificationDto> detectedNotifications = new ArrayList<>();
        crawledNotificationDtos.stream()
                .filter(crawledNotificationDto -> !notificationList.contains(crawledNotificationDto))
                .forEach(detectedNotifications::add);

        notificationList = crawledNotificationDtos;

        return detectedNotifications;
    }
}
