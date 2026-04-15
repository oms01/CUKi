package project.univAlarm.external.sender.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.domain.notification.domain.Notification;

@Getter @Setter
@NoArgsConstructor
public class PushNotificationDto {
    private String schoolName;
    private String kind;
    private String title;
    private String date;
    private String writer;
    private String link;

    public PushNotificationDto(Notification notification) {
        this.schoolName = notification.getNotificationType().getSchool().getName() +"/"+ notification.getNotificationType().getSchool().getCampus();
        this.kind = notification.getNotificationType().getName();
        this.title = notification.getTitle();
        this.date = notification.getDate();
        this.writer = notification.getWriter();
        this.link = notification.getLink();
    }

    public PushNotificationDto(SimpleNotificationDto simpleNotificationDto) {
        this.schoolName = simpleNotificationDto.getSimpleNotificationTypeDto().getSimpleSchoolDto().getName();
        this.kind = simpleNotificationDto.getSimpleNotificationTypeDto().getName();
        this.title = simpleNotificationDto.getTitle();
        this.date = simpleNotificationDto.getDate();
        this.writer = simpleNotificationDto.getWriter();
        this.link = simpleNotificationDto.getLink();
    }

    public String toString() {
        return "["+schoolName+"/"+kind+"/"+date+"/"+writer+"]\n["+title +"]";
    }
}
