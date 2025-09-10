package project.univAlarm.school.service;

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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.common.initialization.dto.SimpleSchoolDto;
import project.univAlarm.school.domain.School;
import project.univAlarm.school.repository.SchoolRepository;
import project.univAlarm.school.dto.SchoolResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<SchoolResponseDto> findAll() {
        List<School> schools = schoolRepository.findAllByOrderByNameAsc();
        List<SchoolResponseDto> response = schools.stream()
                .map(SchoolResponseDto::new)
                .toList();

        return response;
    }

    @Transactional
    public void saveSchools(List<SimpleSchoolDto> simpleSchools){
        String sql = "INSERT INTO schools (name, campus, created_at) VALUES (?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {
                SimpleSchoolDto school = simpleSchools.get(index);
                ps.setString(1, school.getName());
                ps.setString(2, school.getCampus());
                ps.setTimestamp(3, Timestamp.valueOf(now));
            }
            @Override
            public int getBatchSize() {
                return simpleSchools.size();
            }
        });
    }

    @Transactional(readOnly = true)
    public Map<String,SimpleSchoolDto> loadSchoolsId(){
        String sql = "SELECT id, name, campus FROM schools";
        Map<String, SimpleSchoolDto> schoolIds = new HashMap<>();

        jdbcTemplate.query(sql, rs -> {
            String key = rs.getString("name") + ":" + rs.getString("campus");
            SimpleSchoolDto simpleSchoolDto = new SimpleSchoolDto(rs.getString("name"), rs.getString("campus"));
            simpleSchoolDto.setId(rs.getLong("id"));

            schoolIds.put(key, simpleSchoolDto);
        });
        return schoolIds;
    }
}
