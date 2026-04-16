package project.univAlarm.batch.initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.univAlarm.batch.config.UnivConfigProperties;
import project.univAlarm.batch.config.UnivConfigProperties.UnivConfig;
import project.univAlarm.batch.config.UnivConfigProperties.UnivConfig.Campus;
import project.univAlarm.batch.config.UnivConfigProperties.UnivConfig.Campus.UrlEntry;
import project.univAlarm.batch.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.batch.initialization.dto.SimpleSchoolDto;
import project.univAlarm.entity.notificationType.service.NotificationTypeService;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationTypeInitializer {
    private final UnivConfigProperties univConfig;
    private final NotificationTypeService notificationTypeService;

    public Map<String, SimpleNotificationTypeDto> init(Map<String,SimpleSchoolDto> schoolIds){
        List<SimpleNotificationTypeDto> simpleNotificationTypes = getSimpleNotificationTypes(schoolIds);
        notificationTypeService.saveNotificationTypes(simpleNotificationTypes);
        return notificationTypeService.loadNotificationTypes(simpleNotificationTypes, schoolIds);
    }

    private List<SimpleNotificationTypeDto> getSimpleNotificationTypes(Map<String, SimpleSchoolDto> simpleSchools) {
        List<SimpleNotificationTypeDto> simpleNotificationTypes = new ArrayList<>();
        for(UnivConfig config : univConfig.getUnivList()) {
            for(Campus campus : config.getCampuses()) {
                String schoolKey = config.getUnivName() + ":" + campus.getName();

                SimpleSchoolDto simpleSchoolDto = simpleSchools.get(schoolKey);

                if (simpleSchoolDto == null) {
                    log.warn("School not found for key: {}", schoolKey);
                    continue;
                }

                for (UrlEntry entity : campus.getUrls()) {
                    simpleNotificationTypes.add(new SimpleNotificationTypeDto(simpleSchoolDto, entity.getName(), entity.isDepartment(), entity.getUrl()));
                }
            }
        }
        return simpleNotificationTypes;
    }

}
