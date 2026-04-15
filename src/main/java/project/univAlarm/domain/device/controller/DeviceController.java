package project.univAlarm.domain.device.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.univAlarm.common.ApiResponse;
import project.univAlarm.domain.device.dto.DeleteDeviceRequestDto;
import project.univAlarm.domain.device.dto.DeviceRequestDto;
import project.univAlarm.domain.device.dto.UpdateDeviceRequestDto;
import project.univAlarm.common.docs.DeviceControllerDocs;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;
import project.univAlarm.domain.device.service.DeviceService;
import project.univAlarm.domain.device.dto.DeviceResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/devices")
public class DeviceController implements DeviceControllerDocs {
    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DeviceResponseDto>>> getDevices(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = Long.valueOf(userDetails.getUsername());
        List<DeviceResponseDto> devices = deviceService.findAllDevicesByUser(userId);

        return ApiResponse.ok(devices);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeviceRequestDto requestDto
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        DeviceResponseDto device = deviceService.save(userId, requestDto);
        return ApiResponse.noContent();
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateDeviceRequestDto requestDto
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        DeviceResponseDto device = deviceService.update(userId, requestDto);
        return ApiResponse.noContent();
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteDevice(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DeleteDeviceRequestDto requestDto
    ){
        Long userId = Long.valueOf(userDetails.getUsername());
        deviceService.delete(userId, requestDto.getDeviceId());
        return ApiResponse.noContent();
    }
}
