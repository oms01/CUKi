package project.univAlarm.common.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.domain.notificationType.domain.NotificationType.NotificationTypeResponseDto;
import project.univAlarm.domain.school.dto.SchoolResponseDto;

@Tag(name = "School Controller", description = "서비스 중인 학교/공지사항 타입 관련 API 입니다.")
public interface SchoolControllerDocs {

    @CustomOperation(summary = "서비스 중인 모든 학교 가져오기")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponseDto>>> getSchools();

    @CustomOperation(summary = "특정 학교의 서비스 중인 모든 공지사항 타입 가져오기")
    @GetMapping("/{schoolId}/notification-types")
    public ResponseEntity<ApiResponse<List<NotificationTypeResponseDto>>> getNotificationTypeBySchool(
            @PathVariable("schoolId") Long schoolId
    );
}
