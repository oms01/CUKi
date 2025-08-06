package project.univAlarm.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.univAlarm.controller.dto.DeviceRequestDto;
import project.univAlarm.controller.dto.UpdateDeviceRequestDto;

@Entity
@Table(name = "devices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String token;

    @Column(nullable = false)
    private String platform;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Device(User user, String name, String token, String platform) {
        this.user = user;
        this.name = name;
        this.token = token;
        this.platform = platform;
    }

    public Device(User user, DeviceRequestDto deviceRequestDto) {
        this.user = user;
        this.name = deviceRequestDto.getName();
        this.token = deviceRequestDto.getToken();
        this.platform = deviceRequestDto.getPlatform();
    }

    public void updateToken(String token) {
        this.token = token;
    }

    public void updateDeviceInfo(UpdateDeviceRequestDto deviceRequestDto) {
        this.name = deviceRequestDto.getName();
        this.token = deviceRequestDto.getToken();
        this.platform = deviceRequestDto.getPlatform();
    }
}