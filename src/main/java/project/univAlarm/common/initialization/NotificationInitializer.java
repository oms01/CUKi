package project.univAlarm.common.initialization;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.domain.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationInitializer {

    private final NotificationService notificationService;

    public void init(List<SimpleNotificationDto> simpleNotificationDtos){
        notificationService.saveNotifications(simpleNotificationDtos);
    }


}
