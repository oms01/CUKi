package project.univAlarm.school.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.common.docs.SchoolControllerDocs;
import project.univAlarm.notificationType.service.NotificationTypeService;
import project.univAlarm.school.service.SchoolService;
import project.univAlarm.notificationType.domain.NotificationType.NotificationTypeResponseDto;
import project.univAlarm.school.dto.SchoolResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schools")
public class SchoolController implements SchoolControllerDocs {
    private final SchoolService schoolService;
    private final NotificationTypeService notificationTypeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SchoolResponseDto>>> getSchools() {
        List<SchoolResponseDto> schools = schoolService.findAll();
        return ApiResponse.ok(schools);
    }

    @GetMapping("/{schoolId}/notification-types")
    public ResponseEntity<ApiResponse<List<NotificationTypeResponseDto>>> getNotificationTypeBySchool(
            @PathVariable("schoolId") Long schoolId
    ) {
        List<NotificationTypeResponseDto> notificationTypeResponseDtos = notificationTypeService.findBySchool(schoolId);
        return ApiResponse.ok(notificationTypeResponseDtos);
    }
}
