package project.univAlarm.common.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Kakao Login Controller", description = "카카오 로그인 관련 API 입니다.")
public interface LoginControllerDocs {

    @CustomOperation(summary = "카카오 로그인 사용자 회원 검증")
    @GetMapping("/login")
    public ResponseEntity<?> callback(@RequestParam("code") String code);
}
