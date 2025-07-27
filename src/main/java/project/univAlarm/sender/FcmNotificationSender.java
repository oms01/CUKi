package project.univAlarm.sender;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Component;

@Component
public class FcmNotificationSender {

    public boolean send(String target, PushNotificationDto pushNotificationDto) {
        Message message = Message.builder()
                .setNotification(
                        Notification.builder()
                                .setTitle("🔥 새 공지사항이 올라왔어요!")
                                .setBody(pushNotificationDto.toString())
                                .build()
                )
                .setToken(target)
                .build();
        try{
            FirebaseMessaging.getInstance().send(message);
            return true;
        }catch (FirebaseMessagingException e){
            e.printStackTrace();
            return false;
        }
    }
}
