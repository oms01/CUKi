package project.univAlarm.docs;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import project.univAlarm.controller.ApiResponse;
import project.univAlarm.controller.dto.DeleteDeviceRequestDto;
import project.univAlarm.controller.dto.DeviceRequestDto;
import project.univAlarm.controller.dto.UpdateDeviceRequestDto;
import project.univAlarm.jwt.dto.CustomUserDetails;
import project.univAlarm.service.dto.DeviceResponseDto;

@Tag(name = "Device Controller", description = "사용자 장치와 관련된 API 입니다.")
public interface DeviceControllerDocs {

    @CustomOperation(summary = "사용자 장치 가져오기")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeviceResponseDto>>> getDevices(
            @AuthenticationPrincipal CustomUserDetails userDetails
    );


    @CustomOperation(summary = "사용자 장치 등록")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeviceRequestDto requestDto
    );


    @CustomOperation(summary = "사용자 장치 수정")
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateDeviceRequestDto requestDto
    );


    @CustomOperation(summary = "사용자 장치 삭제")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeleteDeviceRequestDto requestDto
    );
}
