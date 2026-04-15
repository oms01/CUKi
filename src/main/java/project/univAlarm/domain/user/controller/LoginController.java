package project.univAlarm.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.common.docs.LoginControllerDocs;
import project.univAlarm.common.security.enums.Role;
import project.univAlarm.external.kakao.KakaoLoginService;
import project.univAlarm.external.kakao.KakaoUserInfoResponseDto;
import project.univAlarm.domain.user.domain.User;
import project.univAlarm.domain.user.dto.UserJoinDto;
import project.univAlarm.domain.user.dto.UserResponseDto;
import project.univAlarm.domain.user.service.LoginService;
import project.univAlarm.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class LoginController implements LoginControllerDocs {

    private final KakaoLoginService kakaoLoginService;
    private final LoginService loginService;
    private final UserService userService;

    @GetMapping("/kakao/login")
    public ResponseEntity<ApiResponse<Void>> callback(@RequestParam("code") String code) {
        String kakaoAccessToken = kakaoLoginService.getAccessTokenFromKakao(code);
        KakaoUserInfoResponseDto userInfo = kakaoLoginService.getUserInfo(kakaoAccessToken); //카카오 유저 정보

        User user = loginService.joinProcess(new UserJoinDto(userInfo), Role.USER.getRoles());
        String accessToken = loginService.createToken(user.getId(), user.getRole());
        String refreshToken = loginService.createRefreshToken(user.getId());
        return ApiResponse.okWithAuthHeader(null, accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new AuthenticationException("refreshToken is empty");
        }

        String userId = loginService.validateRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException("Refresh token is invalid or expired."));

        UserResponseDto userResponseDto = userService.findById(Long.valueOf(userId));

        String newAccessToken = loginService.createToken(Long.valueOf(userId), userResponseDto.getRole());

        return ApiResponse.okWithAuthHeader(null, newAccessToken);
    }
}
