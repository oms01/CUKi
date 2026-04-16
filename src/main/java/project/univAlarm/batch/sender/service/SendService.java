package project.univAlarm.batch.sender.service;

import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.batch.initialization.dto.SimpleNotificationDto;
import project.univAlarm.entity.device.domain.Device;
import project.univAlarm.entity.device.repository.DeviceRepository;
import project.univAlarm.batch.sender.sender.NotificationSender;
import project.univAlarm.batch.sender.dto.PushNotificationDto;
import project.univAlarm.batch.sender.PushNotificationReport;

@Service
@RequiredArgsConstructor
public class SendService {
    private final HashMap<String, NotificationSender> notificationSenders;
    private final DeviceRepository deviceRepository;

    public PushNotificationReport send(SimpleNotificationDto notification) {
        PushNotificationReport report = new PushNotificationReport();

        PushNotificationDto pushNotificationDto = new PushNotificationDto(notification);

        List<Device> targets = deviceRepository.findDevicesByNotificationTypeId(
                notification.getSimpleNotificationTypeDto().getId());

        report.setNotification(pushNotificationDto);
        report.setTotalCount(targets.size());

        for (Device target : targets) {
            String platform = target.getPlatform();
            NotificationSender notificationSender = notificationSenders.get(platform);
            if(notificationSender == null){
                throw new RuntimeException("can't find sender for platform " + platform);
            }

            boolean isSuccess = notificationSender.send(target.getToken(), pushNotificationDto);
            if(isSuccess) {
                report.addSuccess();
            } else {
                report.addFailure();
            }
        }
        return report;
    }
}
