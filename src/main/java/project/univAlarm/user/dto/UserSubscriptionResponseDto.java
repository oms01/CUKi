package project.univAlarm.user.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.subscription.domain.Subscription;

@Getter @Setter
public class UserSubscriptionResponseDto {
    private Long id;
    private Long userId;
    private Long notificationTypeId;
    private String schoolName;
    private String campus;
    private String department;
    private boolean isDepartment;

    public UserSubscriptionResponseDto(Subscription subscription){
        this.id = subscription.getId();
        this.userId = subscription.getUser().getId();
        this.notificationTypeId = subscription.getNotificationType().getId();
        this.schoolName = subscription.getNotificationType().getSchool().getName();
        this.campus = subscription.getNotificationType().getSchool().getCampus();
        this.department = subscription.getNotificationType().getName();
        this.isDepartment = subscription.getNotificationType().getIsDepartment();
    }
}
