package project.univAlarm.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.docs.KakaoLoginControllerDocs;
import project.univAlarm.domain.User;
import project.univAlarm.service.LoginService;
import project.univAlarm.service.dto.UserJoinDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/kakao")
public class KakaoLoginController implements KakaoLoginControllerDocs {

    private final KakaoLoginService kakaoLoginService;
    private final LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        String accessToken = kakaoLoginService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoLoginService.getUserInfo(accessToken); //카카오 유저 정보

        User userEntity = loginService.joinProcess(new UserJoinDto(userInfo),"ROLE_USER");
        String token = loginService.createToken(userEntity,60*60L);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(userEntity);
    }
}
