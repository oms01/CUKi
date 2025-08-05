package project.univAlarm.initialization;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.detector.NotificationDetector;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.School;
import project.univAlarm.service.NotificationTypeService;
import project.univAlarm.service.SchoolService;

@Component
@RequiredArgsConstructor
@Slf4j
public class DetectorPropertiesInitializer {

    private final List<NotificationDetector> detectors;
    private final SchoolService schoolService;
    private final NotificationTypeService notificationTypeService;

    /**
     * detector에 notificationTypeId 저장
     */
    @Transactional(readOnly = true)
    public void initializeDetectorProperties() {
        for (NotificationDetector detector : detectors) {
            String universityName = detector.getUniversityName();
            String campusName = detector.getCampusName();
            Optional<School> school = schoolService.findBySchoolNameAndCampus(universityName,
                    campusName);
            if(school.isEmpty()) {
                log.info("School not found : {} - {}", universityName, campusName);
                continue;
            }
            Optional<NotificationType> notificationType = notificationTypeService.findBySchoolAndDepartment(
                    school.get(), detector.getDepartmentName());
            if(notificationType.isEmpty()) {
                log.info("NotificationType not found : {} - {}", universityName, campusName);
                continue;
            }
            detector.setNotificationTypeId(notificationType.get().getId());

        }
    }
}
