package project.univAlarm.service.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.domain.UserSubscription;

@Getter @Setter
public class UserSubscriptionResponseDto {
    private Long id;
    private Long userId;
    private Long notificationTypeId;
    private String schoolName;
    private String campus;
    private String department;
    private String isDepartment;

    public UserSubscriptionResponseDto(UserSubscription userSubscription){
        this.id = userSubscription.getId();
        this.userId = userSubscription.getUser().getId();
        this.notificationTypeId = userSubscription.getNotificationType().getId();
        this.schoolName = userSubscription.getNotificationType().getSchool().getName();
        this.campus = userSubscription.getNotificationType().getSchool().getCampus();
        this.department = userSubscription.getNotificationType().getName();
        this.isDepartment = userSubscription.getNotificationType().getName();
    }
}
