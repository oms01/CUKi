package project.univAlarm.crawler.catholicUniv;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.univAlarm.batch.crawler.CrawledNotificationDto;
import project.univAlarm.batch.crawler.catholicUniv.CatholicUnivMedicineCrawler;

class CatholicUnivMedicineCrawlerTest {
    private CatholicUnivMedicineCrawler crawler = new CatholicUnivMedicineCrawler();
    private String url = "https://medicine.catholic.ac.kr/bbs/notice/noticelist.jsp"; //의학과

    @Test
    void UrlsCrawlingTest() throws IOException {
        ArrayList<CrawledNotificationDto> notificationDtos = crawler.crawl(url);
        for (CrawledNotificationDto notificationDto : notificationDtos) {
            validateNotification(notificationDto);
        }
    }

    void validateNotification(CrawledNotificationDto notification){
        Assertions.assertNotNull(notification.getTitle());
        Assertions.assertNotNull(notification.getDate());
        Assertions.assertNotNull(notification.getId());
        Assertions.assertNotNull(notification.getLink());
        Assertions.assertNotNull(notification.getWriter());
    }

}