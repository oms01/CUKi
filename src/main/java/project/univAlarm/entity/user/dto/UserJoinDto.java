package project.univAlarm.entity.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.univAlarm.common.security.kakao.KakaoUserInfoResponseDto;

@Getter @Setter
@NoArgsConstructor
public class UserJoinDto {
    private Long kakaoId;
    private String email;
    private String username;
    private String role;

    public UserJoinDto(KakaoUserInfoResponseDto kakaoUserInfoResponseDto) {
        this.kakaoId = kakaoUserInfoResponseDto.getId();
        this.email = kakaoUserInfoResponseDto.getKakaoAccount().getEmail();
        this.username = kakaoUserInfoResponseDto.getProperties().get("nickname");
    }

}
