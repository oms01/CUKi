package project.univAlarm.service.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.domain.School;

@Getter @Setter
public class SchoolResponseDto {
    private Long id;
    private String schoolName;
    private String campus;

    public SchoolResponseDto(School school) {
        this.id = school.getId();
        this.schoolName = school.getName();
        this.campus = school.getCampus();
    }
}
