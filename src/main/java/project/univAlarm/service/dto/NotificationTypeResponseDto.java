package project.univAlarm.service.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.domain.NotificationType;

@Getter @Setter
public class NotificationTypeResponseDto {
    private Long id;
    private String schoolName;
    private String name;
    private boolean isDepartment;
    private String url;

    public NotificationTypeResponseDto(NotificationType notificationType) {
        this.id = notificationType.getId();
        this.schoolName = notificationType.getSchool().getName();
        this.name = notificationType.getName();
        this.isDepartment = notificationType.getIsDepartment();
        this.url = notificationType.getUrl();
    }
}
