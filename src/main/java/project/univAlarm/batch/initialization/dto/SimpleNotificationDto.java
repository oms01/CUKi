package project.univAlarm.batch.initialization.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.batch.crawler.CrawledNotificationDto;

@Getter @Setter
public class SimpleNotificationDto {
    private SimpleNotificationTypeDto simpleNotificationTypeDto;
    private Long originId;
    private String title;
    private String link;
    private String date;
    private String writer;

    public SimpleNotificationDto(SimpleNotificationTypeDto simpleNotificationTypeDto, CrawledNotificationDto crawledNotificationDto) {
        this.simpleNotificationTypeDto = simpleNotificationTypeDto;
        this.originId = crawledNotificationDto.getId();
        this.title = crawledNotificationDto.getTitle();
        this.link = crawledNotificationDto.getLink();
        this.date = crawledNotificationDto.getDate();
        this.writer = crawledNotificationDto.getWriter();
    }
}
