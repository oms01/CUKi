package project.univAlarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.docs.UserControllerDocs;
import project.univAlarm.jwt.dto.CustomUserDetails;
import project.univAlarm.service.UserService;
import project.univAlarm.service.dto.UserResponseDto;

@RestController
@RequestMapping("/api/v1/user/profile")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        UserResponseDto user = userService.findById(userId);
        return ApiResponse.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        userService.delete(userId);
        return ApiResponse.noContent();
    }
}
