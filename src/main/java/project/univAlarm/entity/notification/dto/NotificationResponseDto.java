package project.univAlarm.entity.notification.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.entity.notification.domain.Notification;

@Getter @Setter
public class NotificationResponseDto {

    private Long id;
    private String schoolName;
    private String department;
    private String title;
    private String writer;
    private String link;
    private String date;

    public NotificationResponseDto(Notification notification) {
        this.id = notification.getId();
        this.schoolName = notification.getNotificationType().getSchool().getName();
        this.department = notification.getNotificationType().getName();
        this.title = notification.getTitle();
        this.writer = notification.getWriter();
        this.link = notification.getLink();
        this.date = notification.getDate();
    }
}
