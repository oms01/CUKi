package project.univAlarm.batch.crawler.catholicUniv;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import project.univAlarm.batch.crawler.CrawledNotificationDto;
import project.univAlarm.batch.crawler.Crawler;

@Component
public class CatholicUnivDefaultCrawler implements Crawler {
    @Override
    public ArrayList<CrawledNotificationDto> crawl(String baseUrl) throws IOException {
        Document doc = Jsoup.connect(baseUrl).timeout(30000).get();

        Elements rows = doc.select("tbody tr");
        ArrayList<CrawledNotificationDto> notifications = new ArrayList<>();

        for (Element row : rows) {
            CrawledNotificationDto notification = extractNotification(row, baseUrl);
            if (notification != null)
                notifications.add(notification);
        }
        return notifications;
    }

    private CrawledNotificationDto extractNotification(Element row, String baseUrl){
        if(row.hasClass(" b-cate-notice")) {
            return null;
        }

        Elements tds = row.select("td");
        Element link = row.selectFirst("a.b-title");
        if(link==null) return null;
        int len = tds.size();

        Long id = Long.parseLong(tds.get(0).text());
        String title = link.text().substring(0, Math.min(250, link.text().length()));
        String href = link.attr("href");
        String date = tds.get(len-3).text();
        String writer = tds.get(len-2).text();

        if(baseUrl.contains("campuslife")) baseUrl = "https://www.catholic.ac.kr/ko/campuslife/notice.do";

        return new CrawledNotificationDto(id, title, date, writer, baseUrl+href);
    }
}
