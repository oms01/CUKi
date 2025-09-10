package project.univAlarm.common.initialization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SimpleNotificationTypeDto {
    private Long id;
    private SimpleSchoolDto simpleSchoolDto;
    private String name;
    private Boolean isDepartment;
    private String url;
    public SimpleNotificationTypeDto(SimpleSchoolDto simpleSchoolDto, String name, Boolean isDepartment, String url) {
        this.simpleSchoolDto = simpleSchoolDto;
        this.name = name;
        this.isDepartment = isDepartment;
        this.url = url;
    }

    @Override
    public String toString() {
        return simpleSchoolDto.toString() + " | " + name + " | " + isDepartment + " | " + url +"| : ";
    }
}
