package project.univAlarm.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.service.NotificationTypeService;
import project.univAlarm.service.SchoolService;
import project.univAlarm.service.dto.NotificationTypeResponseDto;
import project.univAlarm.service.dto.SchoolResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schools")
public class SchoolController {
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
