package project.univAlarm.common.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTPayload {
    private Long userId;
    private String role;
}
