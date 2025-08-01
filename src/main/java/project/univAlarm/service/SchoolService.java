package project.univAlarm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.domain.School;
import project.univAlarm.repository.SchoolRepository;
import project.univAlarm.service.dto.SchoolResponseDto;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    @Transactional(readOnly = true)
    public List<SchoolResponseDto> findAll() {
        List<School> schools = schoolRepository.findAll();
        return schools.stream()
                .map(SchoolResponseDto::new)
                .toList();
    }
}
