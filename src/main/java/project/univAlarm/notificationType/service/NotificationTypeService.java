package project.univAlarm.notificationType.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.common.initialization.dto.SimpleNotificationTypeDto;
import project.univAlarm.common.initialization.dto.SimpleSchoolDto;
import project.univAlarm.notificationType.domain.NotificationType;
import project.univAlarm.school.domain.School;
import project.univAlarm.notificationType.repository.NotificationTypeRepository;
import project.univAlarm.notificationType.domain.NotificationType.NotificationTypeResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationTypeService {
    private final NotificationTypeRepository notificationTypeRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<NotificationTypeResponseDto> findBySchool(Long school) {
        List<NotificationType> notificationTypes = notificationTypeRepository.findBySchoolIdOrderByIsDepartmentAscNameAsc(school);
        List<NotificationTypeResponseDto> response = notificationTypes.stream()
                .map(NotificationTypeResponseDto::new)
                .toList();
        return response;
    }

    public void saveNotificationTypes(List<SimpleNotificationTypeDto> simpleNotificationTypes) {
        String sql = "INSERT INTO notification_types (school_id, name, is_department, url, created_at) VALUES (?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                SimpleNotificationTypeDto simpleNotificationType = simpleNotificationTypes.get(index);
                ps.setLong(1, simpleNotificationType.getSimpleSchoolDto().getId());
                ps.setString(2, simpleNotificationType.getName());
                ps.setBoolean(3, simpleNotificationType.getIsDepartment());
                ps.setString(4, simpleNotificationType.getUrl());
                ps.setTimestamp(5, Timestamp.valueOf(now));
            }
            @Override
            public int getBatchSize() {
                return simpleNotificationTypes.size();
            }
        });
    }

    public Map<String,SimpleNotificationTypeDto> loadNotificationTypes(List<SimpleNotificationTypeDto> simpleNotificationTypes, Map<String, SimpleSchoolDto> simpleSchools){
        String sql = "SELECT id, url FROM notification_types";
        Map<String, SimpleNotificationTypeDto> result = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            String url = rs.getString("url");
            Long id = rs.getLong("id");
            Optional<SimpleNotificationTypeDto> notificationTypeDto = simpleNotificationTypes.stream()
                    .filter(s -> s.getUrl().equals(url)).findFirst();
            if(notificationTypeDto.isEmpty()) {
                log.warn("School not found for id: {}", id);
            }
            else{
                SimpleNotificationTypeDto dto = notificationTypeDto.get();
                dto.setId(id);
                result.put(url, dto);
            }
        });
        return result;
    }
<<<<<<< HEAD
=======


>>>>>>> 2294ff5 (Refactor Init)
}
