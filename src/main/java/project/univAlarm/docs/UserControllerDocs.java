package project.univAlarm.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import project.univAlarm.controller.ApiResponse;
import project.univAlarm.jwt.dto.CustomUserDetails;
import project.univAlarm.service.dto.UserResponseDto;

@Tag(name = "User Controller", description = "사용자 정보 관련 API 입니다.")
public interface UserControllerDocs {

    @CustomOperation(summary = "사용자 정보 가져오기")
    @GetMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    );


    @CustomOperation(summary = "사용자 탈퇴")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
