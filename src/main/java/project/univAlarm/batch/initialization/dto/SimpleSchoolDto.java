package project.univAlarm.batch.initialization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SimpleSchoolDto {
    private Long id;
    private String name;
    private String campus;
    public SimpleSchoolDto(String name, String campus) {
        this.name = name;
        this.campus = campus;
    }

    @Override
    public String toString() {
        return "["+ campus + " | " + name+" ]";
    }
}
