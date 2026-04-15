package project.univAlarm.common.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.univAlarm.common.ApiResponse;

@Tag(name = "Kakao Login Controller", description = "카카오 로그인 관련 API 입니다.")
public interface LoginControllerDocs {

    @CustomOperation(summary = "카카오 로그인 사용자 회원 검증")
    @GetMapping("/login")
    public ResponseEntity<?> callback(@RequestParam("code") String code);

    @CustomOperation(summary = "RefreshToken 검증 및 AccessToken 발급")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<Void>> refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException;
}
