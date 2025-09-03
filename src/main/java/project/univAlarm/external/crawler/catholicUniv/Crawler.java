package project.univAlarm.external.crawler.catholicUniv;

import java.io.IOException;
import java.util.ArrayList;
import project.univAlarm.external.crawler.CrawledNotificationDto;

public interface Crawler {
    ArrayList<CrawledNotificationDto> crawl(String baseUrl) throws IOException;
}
