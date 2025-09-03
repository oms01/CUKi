package project.univAlarm.crawler.catholicUniv;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.external.crawler.catholicUniv.CatholicUnivDefaultCrawler;

class CatholicUnivDefaultCrawlerTest {

    private CatholicUnivDefaultCrawler crawler = new CatholicUnivDefaultCrawler();
    private String[] urls = {
            "https://www.catholic.ac.kr/ko/campuslife/notice.do?mode=list&srCategoryId=20&srSearchKey=article_title&srSearchVal=", //기본
            "https://songsin.catholic.ac.kr/ko/songsin-community/notice.do?mode=list&&articleLimit=10&article.offset=0", //신학과
            "https://csie.catholic.ac.kr/csie/community/notice.do", //컴퓨터정보공학부
    };

    @Test
    void UrlsCrawlingTest() throws IOException {
        for (String url : urls) {
            ArrayList<CrawledNotificationDto> notificationDtos = crawler.crawl(url);
            for (CrawledNotificationDto notificationDto : notificationDtos) {
                validateNotification(notificationDto);
            }
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