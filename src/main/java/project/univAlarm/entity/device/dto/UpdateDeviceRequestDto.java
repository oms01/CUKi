package project.univAlarm.entity.device.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDeviceRequestDto {
    private Long id;
    private String name;
    private String token;
    private String platform;
}
