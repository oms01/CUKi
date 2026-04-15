package project.univAlarm.domain.subscription.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionRequestDto {
    private List<Long> notificationTypeIds;
}
