package project.univAlarm.batch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.batch.detector.DetectorManager;
import project.univAlarm.batch.initialization.*;
import project.univAlarm.batch.initialization.domainInitializer.*;
import project.univAlarm.batch.initialization.dto.SimpleNotificationDto;
import project.univAlarm.batch.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.batch.initialization.dto.SimpleSchoolDto;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;
import project.univAlarm.common.utils.DateFormatter;
import project.univAlarm.entity.device.dto.DeviceResponseDto;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
@Slf4j
public class BatchController {

    private final SchoolInitializer schoolInitializer;
    private final NotificationTypeInitializer notificationTypeInitializer;
    private final NotificationInitializer notificationInitializer;
    private final DetectorPropertiesInitializer detectorPropertiesInitializer;
    private final DetectorInitializer detectorInitializer;
    private final AdminAccountInitializer adminAccountInitializer;
    private final DetectorManager detectorManager;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DeviceResponseDto>>> init(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Map<String, SimpleSchoolDto> schoolIds = schoolInitializer.init(); // 학교명:교정명 -> 학교정보
        Map<String, SimpleNotificationTypeDto> notificationTypeIds = notificationTypeInitializer.init(schoolIds); //url -> 공지 종류
        detectorPropertiesInitializer.init(notificationTypeIds);
        log.info("[{}] Detector Properties Initializing Complete", DateFormatter.currentTimeFormatted());

        long st = System.currentTimeMillis();
        List<SimpleNotificationDto> simpleNotificationDtos = detectorInitializer.init();
        long en = System.currentTimeMillis();
        log.info("[{}] Detector Initializing Complete {}ms", DateFormatter.currentTimeFormatted(), en - st);
        notificationInitializer.init(simpleNotificationDtos);
        log.info("[{}] Notification Data Initializing Complete", DateFormatter.currentTimeFormatted());

        adminAccountInitializer.init();
        log.info("[{}] Admin Account Initializing Complete", DateFormatter.currentTimeFormatted());

        log.info("[{}] DetectorManager start running", DateFormatter.currentTimeFormatted());
        detectorManager.startScheduledTask();
        return ApiResponse.ok();
    }

}
