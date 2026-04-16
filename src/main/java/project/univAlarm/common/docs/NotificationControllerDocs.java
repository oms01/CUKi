package project.univAlarm.common.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;
import project.univAlarm.entity.notification.dto.NotificationResponseDto;

@Tag(name = "Notification Controller", description = "사용자가 구독한 공지사항 관련 API 입니다.")
public interface NotificationControllerDocs {

    @CustomOperation(summary = "사용자가 구독한 공지사항 가져오기")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getSubscribedNotifications(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue="0") int page
    );

    @CustomOperation(summary = "공지사항 검색하기")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> searchNotifications(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page
    );
}
