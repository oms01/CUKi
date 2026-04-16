package project.univAlarm.batch.config;

import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.univAlarm.batch.sender.sender.FcmNotificationSender;
import project.univAlarm.batch.sender.sender.NotificationSender;

@Configuration
public class NotificationSenderConfig {

    @Bean
    public HashMap<String, NotificationSender> notificationSenderMap(){
        HashMap<String, NotificationSender> notificationSenderMap = new HashMap<>();

        // sender 추가될 때 마다 아래에 추가하기
        FcmNotificationSender fcmNotificationSender = new FcmNotificationSender();
        notificationSenderMap.put("Android", fcmNotificationSender);

        return notificationSenderMap;
    }
}
