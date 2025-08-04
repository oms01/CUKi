package project.univAlarm.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeviceRequestDto {
    private Long userId;
    private String name;
    private String token;
    private String platform;

    @Builder
    public DeviceRequestDto(Long userId, String name, String token, String platform) {
        this.userId = userId;
        this.name = name;
        this.token = token;
        this.platform = platform;
    }
}
