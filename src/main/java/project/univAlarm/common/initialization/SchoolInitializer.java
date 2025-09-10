package project.univAlarm.common.initialization;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.common.config.UnivConfigProperties;
import project.univAlarm.common.config.UnivConfigProperties.UnivConfig;
import project.univAlarm.common.config.UnivConfigProperties.UnivConfig.Campus;
import project.univAlarm.common.config.UnivConfigProperties.UnivConfig.Campus.UrlEntry;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.initialization.dto.SimpleSchoolDto;
import project.univAlarm.school.repository.SchoolRepository;
import project.univAlarm.school.service.SchoolService;

@Component
@RequiredArgsConstructor
public class SchoolInitializer {
    private final UnivConfigProperties univConfig;
    private final SchoolService schoolService;

    /**
     * school, notification Type 저장
     */
    @Transactional
    public Map<String,SimpleSchoolDto> init() {
        List<SimpleSchoolDto> simpleSchools = getSimpleSchools();
        schoolService.saveSchools(simpleSchools);
        return schoolService.loadSchoolsId();
    }

    private List<SimpleSchoolDto> getSimpleSchools() {
        List<SimpleSchoolDto> schools = new ArrayList<>();
        for(UnivConfig config : univConfig.getUnivList()) {
            for(Campus campus : config.getCampuses()) {
                schools.add(new SimpleSchoolDto(config.getUnivName(), campus.getName()));
            }
        }
        return schools;
    }

}
