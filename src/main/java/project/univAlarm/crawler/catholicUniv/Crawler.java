package project.univAlarm.crawler.catholicUniv;

import java.io.IOException;
import java.util.ArrayList;
import project.univAlarm.crawler.CrawledNotificationDto;

public interface Crawler {
    ArrayList<CrawledNotificationDto> crawl(String baseUrl) throws IOException;
}
