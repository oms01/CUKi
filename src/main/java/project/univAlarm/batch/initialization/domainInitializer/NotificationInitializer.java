package project.univAlarm.batch.initialization.domainInitializer;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.univAlarm.batch.initialization.dto.SimpleNotificationDto;
import project.univAlarm.entity.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationInitializer {

    private final NotificationService notificationService;

    public void init(List<SimpleNotificationDto> simpleNotificationDtos){
        notificationService.saveNotifications(simpleNotificationDtos);
    }


}
