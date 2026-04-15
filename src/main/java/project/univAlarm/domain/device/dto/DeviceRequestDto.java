package project.univAlarm.domain.device.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeviceRequestDto {
    private Long userId;
    private String name;
    private String token;
    private String platform;
    private String model;

    @Builder
    public DeviceRequestDto(Long userId, String name, String token, String platform, String model) {
        this.userId = userId;
        this.name = name;
        this.token = token;
        this.platform = platform;
        this.model = model;
    }
}
