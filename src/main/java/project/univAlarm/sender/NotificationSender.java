package project.univAlarm.sender;

public interface NotificationSender {
    boolean send(String target, PushNotificationDto pushNotificationDto);
}
