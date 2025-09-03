package project.univAlarm.external.crawler;

import java.util.Objects;
import lombok.Getter;

@Getter
public class CrawledNotificationDto {
    private final Long id;
    private final String title;
    private final String date;
    private final String writer;
    private final String link;

    public CrawledNotificationDto(Long id, String title, String date, String writer, String link) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.writer = writer;
        this.link = link;
    }

    @Override
    public String toString() {
        return "["+date+"/"+writer+"/"+id+"]\n["+title +"]("+link+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrawledNotificationDto that = (CrawledNotificationDto) o;
        return Objects.equals(link, that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, writer, link);
    }
}
