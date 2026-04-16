package project.univAlarm.entity.user.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import project.univAlarm.entity.user.domain.User;

@Getter @Setter
public class UserResponseDto {
    private Long kakaoId;
    private String email;
    private String username;
    private String role;
    private LocalDateTime createdAt;

    public UserResponseDto(User user) {
        this.kakaoId = user.getKakaoId();
        this.email = user.getEmail();
        this.username = user.getName();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }

}
