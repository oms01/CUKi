package project.univAlarm.service.dto;

import lombok.Getter;
import lombok.Setter;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserDevice;

@Getter
@Setter
public class DeviceResponseDto {
    private Long id;
    private User user;
    private String name;
    private String token;
    private String platform;

    public DeviceResponseDto(UserDevice device) {
        this.id = device.getId();
        this.user = device.getUser();
        this.name = device.getName();
        this.token = device.getToken();
        this.platform = device.getPlatform();
    }
}
