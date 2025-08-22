package project.univAlarm.external.sender.service;

import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.notification.domain.Notification;
import project.univAlarm.device.domain.Device;
import project.univAlarm.device.repository.DeviceRepository;
import project.univAlarm.external.sender.sender.NotificationSender;
import project.univAlarm.external.sender.dto.PushNotificationDto;
import project.univAlarm.external.sender.PushNotificationReport;

@Service
@RequiredArgsConstructor
public class SendService {
    private final HashMap<String, NotificationSender> notificationSenders;
    private final DeviceRepository deviceRepository;

    public PushNotificationReport send(Notification notification) {
        PushNotificationReport report = new PushNotificationReport();

        PushNotificationDto pushNotificationDto = new PushNotificationDto(notification);

        List<Device> targets = deviceRepository.findByNotificationTypeId(
                notification.getNotificationType().getId());

        report.setNotification(pushNotificationDto);
        report.setTotalCount(targets.size());

        for (Device target : targets) {
            String platform = target.getPlatform();
            NotificationSender notificationSender = notificationSenders.get(platform);
            if (notificationSender != null) {
                boolean isSuccess = notificationSender.send(target.getToken(), pushNotificationDto);
                if(isSuccess) {
                    report.addSuccess();
                } else{
                    report.addFailure();
                }
            }
        }
        return report;
    }
}
