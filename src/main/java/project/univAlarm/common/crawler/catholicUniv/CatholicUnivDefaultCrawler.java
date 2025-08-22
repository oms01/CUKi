package project.univAlarm.common.crawler.catholicUniv;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import project.univAlarm.common.crawler.CrawledNotificationDto;

@Component
public class CatholicUnivDefaultCrawler implements Crawler {
    @Override
    public ArrayList<CrawledNotificationDto> crawl(String baseUrl) throws IOException {
        Document doc = Jsoup.connect(baseUrl).get();

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
        String title = link.text();
        String href = link.attr("href");
        String date = tds.get(len-3).text();
        String writer = tds.get(len-2).text();

        if(baseUrl.contains("campuslife")) baseUrl = "https://www.catholic.ac.kr/ko/campuslife/notice.do";

        return new CrawledNotificationDto(id, title, date, writer, baseUrl+href);
    }
}
