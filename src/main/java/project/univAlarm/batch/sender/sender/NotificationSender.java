package project.univAlarm.batch.sender.sender;

import project.univAlarm.batch.sender.dto.PushNotificationDto;

public interface NotificationSender {
    boolean send(String target, PushNotificationDto pushNotificationDto);
}
