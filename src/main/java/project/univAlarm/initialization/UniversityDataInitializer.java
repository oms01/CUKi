package project.univAlarm.initialization;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.config.UnivConfigProperties;
import project.univAlarm.config.UnivConfigProperties.UnivConfig;
import project.univAlarm.config.UnivConfigProperties.UnivConfig.Campus;
import project.univAlarm.config.UnivConfigProperties.UnivConfig.Campus.UrlEntry;
import project.univAlarm.domain.NotificationType;
import project.univAlarm.domain.School;
import project.univAlarm.repository.NotificationTypeRepository;
import project.univAlarm.repository.SchoolRepository;

@Component
@RequiredArgsConstructor
public class UniversityDataInitializer {
    private final UnivConfigProperties univConfig;
    private final SchoolRepository schoolRepository;
    private final NotificationTypeRepository notificationTypeRepository;

    @Transactional
    public void initializeUniversityData() {
        for(UnivConfig config : univConfig.getUnivList()) {
            for(Campus campus : config.getCampuses()) {
                School school = saveOrUpdateSchool(config.getUnivName(), campus.getName());
                for (UrlEntry entity : campus.getUrls()) {
                    saveOrUpdateNotificationType(school, entity.getName(), entity.isDepartment(), entity.getUrl());
                }
            }
        }
    }

    private School saveOrUpdateSchool(String UnivName, String CampusName) {
        Optional<School> School = schoolRepository.findByName(UnivName);
        return School.orElseGet(() -> schoolRepository.save(new School(UnivName, CampusName)));
    }

    private NotificationType saveOrUpdateNotificationType(School school, String departmentName, Boolean isDepartment, String url) {
        Optional<NotificationType> notificationType = notificationTypeRepository.findBySchoolIdAndName(school.getId(),
                departmentName);
        return notificationType.orElseGet(() -> notificationTypeRepository.save(new NotificationType(school, departmentName, isDepartment, url)));
    }
}
