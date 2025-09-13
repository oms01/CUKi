package project.univAlarm.common.detector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import project.univAlarm.common.initialization.dto.SimpleNotificationDto;
import project.univAlarm.external.crawler.CrawledNotificationDto;
import project.univAlarm.notification.domain.Notification;
import project.univAlarm.external.sender.sender.DiscordReportSender;
import project.univAlarm.external.sender.dto.PushNotificationDto;
import project.univAlarm.external.sender.PushNotificationReport;
import project.univAlarm.notification.service.NotificationService;
import project.univAlarm.external.sender.service.SendService;
import project.univAlarm.common.utils.DateFormatter;
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

    @Value("${scheduler.detector.thread_count}")
    private int threadCount;
    @Value("${scheduler.detector.interval}")
    private int executionIntervalSeconds;

    private ScheduledExecutorService detectorExecutor;

    public void startScheduledTask() {
        detectorExecutor = Executors.newScheduledThreadPool(threadCount); //스레드 풀 생성

        int intervalBetweenDetectors = executionIntervalSeconds * 1000 / detectors.size(); // 밀리초 단위

        log.info("Starting {} detectors with {} threads, execution interval: {}s, detector interval: {}ms",
                detectors.size(), threadCount, executionIntervalSeconds, intervalBetweenDetectors);

        for (int i = 0; i < detectors.size(); i++) {
            NotificationDetector detector = detectors.get(i);
            final int initialDelay = i * intervalBetweenDetectors;

            detectorExecutor.scheduleAtFixedRate(() -> {
                executeDetectorAsync(detector);
            }, initialDelay, executionIntervalSeconds * 1000L, TimeUnit.MILLISECONDS);
        }
    }

    private void executeDetectorAsync(NotificationDetector detector) {
        CompletableFuture.runAsync(() -> {
            try {
//                log.info("[{}] Detector {} starting execution", DateFormatter.currentTimeFormatted(), detector.getSimpleNotificationTypeDto().getName());
                runSingleDetector(detector);
            } catch (Exception e) {
                log.error("Detector {} failed: {}", detector.getSimpleNotificationTypeDto().toString(), e.getMessage());
            }
        }, detectorExecutor);
    }

    public void runSingleDetector(NotificationDetector detector) throws IOException{
        List<CrawledNotificationDto> crawledNotificationDtos = detector.runDetector();

        List<SimpleNotificationDto> updatedNotifications = new ArrayList<>();
        for (CrawledNotificationDto crawledNotificationDto : crawledNotificationDtos) {
            boolean exist = notificationService.isExist(detector.getSimpleNotificationTypeDto().getId(),
                    crawledNotificationDto.getId());
            if (!exist) updatedNotifications.add(new SimpleNotificationDto(detector.getSimpleNotificationTypeDto(), crawledNotificationDto));
        }

        notificationService.saveNotifications(updatedNotifications);

        for (SimpleNotificationDto updatedNotification : updatedNotifications) {
            PushNotificationReport report = sendService.send(updatedNotification);
            sendDiscordReport(report, updatedNotification);
        }
    }

    void sendDiscordReport(PushNotificationReport report, SimpleNotificationDto notification) {
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
}
