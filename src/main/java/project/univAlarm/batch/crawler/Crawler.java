package project.univAlarm.batch.crawler;

import java.io.IOException;
import java.util.ArrayList;

public interface Crawler {
    ArrayList<CrawledNotificationDto> crawl(String baseUrl) throws IOException;
}
