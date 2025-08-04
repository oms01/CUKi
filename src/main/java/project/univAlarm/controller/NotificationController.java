package project.univAlarm.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.jwt.dto.CustomUserDetails;
import project.univAlarm.service.NotificationService;
import project.univAlarm.service.dto.NotificationResponseDto;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getSubscribedNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue="0") int page
            ){
        Long userId = Long.valueOf(userDetails.getUsername());
        List<NotificationResponseDto> notificationList = notificationService.findSubscribedNotificationByUser(
                userId, page);

        return ApiResponse.ok(notificationList);
    }
}
