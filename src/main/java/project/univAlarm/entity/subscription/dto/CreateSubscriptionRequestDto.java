package project.univAlarm.entity.subscription.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionRequestDto {
    private List<Long> notificationTypeIds;
}
