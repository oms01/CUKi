package project.univAlarm.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.univAlarm.domain.User;

@Getter @Setter
public class DeviceRequestDto {
    private User user;
    private String name;
    private String token;
    private String platform;

    @Builder
    public DeviceRequestDto(User user, String name, String token, String platform) {
        this.user = user;
        this.name = name;
        this.token = token;
        this.platform = platform;
    }
}
