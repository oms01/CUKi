package project.univAlarm.service;

import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.Notification;
import project.univAlarm.domain.UserDevice;
import project.univAlarm.repository.UserDeviceRepository;
import project.univAlarm.sender.NotificationSender;
import project.univAlarm.sender.PushNotificationDto;
import project.univAlarm.sender.PushNotificationReport;

@Service
@RequiredArgsConstructor
public class SendService {
    private final HashMap<String, NotificationSender> notificationSenders;
    private final UserDeviceRepository userDeviceRepository;

    public PushNotificationReport send(Notification notification) {
        PushNotificationReport report = new PushNotificationReport();

        PushNotificationDto pushNotificationDto = new PushNotificationDto(notification);

        List<UserDevice> targets = userDeviceRepository.findByNotificationTypeId(
                notification.getNotificationType().getId());

        report.setNotification(pushNotificationDto);
        report.setTotalCount(targets.size());

        for (UserDevice target : targets) {
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
