package project.univAlarm.detector;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.univAlarm.common.detector.NotificationDetector;
import project.univAlarm.external.crawler.CrawledNotificationDto;

@SpringBootTest
class NotificationDetectorTest {

    @Autowired
    private List<NotificationDetector> detectors;

    @Test
    void crawlAllCatholicUnivDepartment(){
        for (NotificationDetector detector : detectors) {
            if(detector.getNotificationList().isEmpty()){
                Assertions.fail("Crawling failed! : " + detector.getUniversityName() + "-" + detector.getDepartmentName());
            }
            for (CrawledNotificationDto notificationDto : detector.getNotificationList()) {
//                System.out.println(notificationDto.toString());
                validateNotification(notificationDto);
            }

        }
    }

    void validateNotification(CrawledNotificationDto notification) {
        Assertions.assertNotNull(notification.getId());
        Assertions.assertNotNull(notification.getTitle());
        Assertions.assertNotNull(notification.getDate());
        Assertions.assertNotNull(notification.getWriter());
        Assertions.assertNotNull(notification.getLink());
    }


}