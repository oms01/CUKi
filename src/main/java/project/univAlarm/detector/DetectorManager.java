package project.univAlarm.detector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import project.univAlarm.crawler.CrawledNotificationDto;
import project.univAlarm.domain.Notification;
import project.univAlarm.sender.DiscordReportSender;
import project.univAlarm.sender.PushNotificationDto;
import project.univAlarm.sender.PushNotificationReport;
import project.univAlarm.service.NotificationService;
import project.univAlarm.service.SendService;
import project.univAlarm.utils.DateFormatter;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
public class DetectorManager {

    private final List<NotificationDetector> detectors;
    private final NotificationService notificationService;
    private final SendService sendService;
    private final DiscordReportSender discordReportSender;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void startScheduledTask(){
        scheduler.scheduleAtFixedRate(()->{
            try{
                run();
            } catch (Exception e){
                log.error("Scheduled task failed", e);
            }
        },0,60, TimeUnit.SECONDS);
    }

    public void run() throws IOException {
        long start = System.currentTimeMillis();

        List<Notification> updatedNotifications = new ArrayList<>();

        for (NotificationDetector detector : detectors) {
            
            // 새로 감지된 공지사항 가져오기
            List<CrawledNotificationDto> crawledNotificationDtos = detector.runDetector();

            // 저장 성공한 공지 가져오기
            List<Notification> savedNotifications = notificationService.saveNotifications(detector.getNotificationType(),
                    crawledNotificationDtos);
            updatedNotifications.addAll(savedNotifications);
        }


        for (Notification notification : updatedNotifications) {
            PushNotificationReport report = sendService.send(notification);

            PushNotificationReport pushNotificationReport = new PushNotificationReport();
            pushNotificationReport.setNotification(new PushNotificationDto(notification));
            pushNotificationReport.addCount(report.getTotalCount(), report.getSuccessCount(), report.getFailureCount());

            Mono<Boolean> send = discordReportSender.send(pushNotificationReport);

            send.subscribe(
                    success -> {
                        if(success) {
                            log.info("Notification sent successfully");
                        } else {
                            log.info("Notification sent failed");
                        }
                    }
            );
        }

        long end = System.currentTimeMillis();
        log.info("[{}] {} Change Detector finished in {} ms", DateFormatter.currentTimeFormatted(), detectors.size(), end - start);
    }
}
