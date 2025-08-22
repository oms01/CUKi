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
public class CatholicUnivNursingCrawler implements Crawler {

    @Override
    public ArrayList<CrawledNotificationDto> crawl(String baseUrl) throws IOException {
        Document doc = Jsoup.connect(baseUrl).get();
        Elements rows = doc.select("table.board_list > tbody > tr");
        ArrayList<CrawledNotificationDto> notifications = new ArrayList<>();
        for (Element row : rows) {
            CrawledNotificationDto notification = extractNotification(row);
            if(notification != null) notifications.add(notification);
        }
        return notifications;
    }

    private CrawledNotificationDto extractNotification(Element row) {
        Elements tds = row.select("td");
        Element link = row.selectFirst("a");
        if(link==null) return null;

        Long id = Long.parseLong(tds.get(0).text());
        String title = link.text();
        String href = link.attr("href");
        String date = tds.get(4).text();
        String writer = tds.get(3).text();

        return new CrawledNotificationDto(id, title, date, writer, href);
    }
}
