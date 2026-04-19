package project.univAlarm.entity.notification.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.common.docs.NotificationControllerDocs;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;
import project.univAlarm.entity.notification.service.NotificationService;
import project.univAlarm.entity.notification.dto.NotificationResponseDto;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerDocs {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getSubscribedNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String lastDate,
            @RequestParam(required = false) Long lastId
            ){
        Long userId = Long.valueOf(userDetails.getUsername());
        List<NotificationResponseDto> notificationList = notificationService.findSubscribedNotificationByUser(
                userId, lastDate, lastId);

        return ApiResponse.ok(notificationList);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> searchNotifications(
            @RequestParam String keyword,
            @RequestParam(required = false) String lastDate,
            @RequestParam(required = false) Long lastId
    ) {
        List<NotificationResponseDto> notificationList = notificationService.searchNotifications(keyword, lastDate, lastId);
        return ApiResponse.ok(notificationList);
    }

    @GetMapping("/search/v2")
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> searchNotificationsV2(
            @RequestParam String keyword,
            @RequestParam(required = false) String lastDate,
            @RequestParam(required = false) Long lastId
    ) {
        List<NotificationResponseDto> notificationList = notificationService.searchNotificationsV2(keyword, lastDate, lastId);
        return ApiResponse.ok(notificationList);
    }
}
