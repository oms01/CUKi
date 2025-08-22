package project.univAlarm.external.sender.sender;

import project.univAlarm.external.sender.dto.PushNotificationDto;

public interface NotificationSender {
    boolean send(String target, PushNotificationDto pushNotificationDto);
}
