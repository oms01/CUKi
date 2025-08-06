package project.univAlarm.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.controller.dto.DeviceRequestDto;
import project.univAlarm.controller.dto.UpdateDeviceRequestDto;
import project.univAlarm.domain.User;
import project.univAlarm.domain.Device;
import project.univAlarm.repository.DeviceRepository;
import project.univAlarm.repository.UserRepository;
import project.univAlarm.service.dto.DeviceResponseDto;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Transactional
    public DeviceResponseDto save(Long userId, DeviceRequestDto userDevice){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return null;
        }
        Device device = deviceRepository.save(new Device(user.get(), userDevice));
        return new DeviceResponseDto(device);
    }

    @Transactional
    public DeviceResponseDto update(Long userId, UpdateDeviceRequestDto userDevice){
        Device device = findDeviceOrThrow(userId, userDevice.getId());
        device.updateDeviceInfo(userDevice);
        return new DeviceResponseDto(device);
    }

    @Transactional
    public void delete(Long userId, Long deviceId){
        Device device = findDeviceOrThrow(userId, deviceId);
        deviceRepository.delete(device);
    }

    @Transactional(readOnly = true)
    public List<DeviceResponseDto> findAllDevicesByUser(Long userId){
        List<Device> devices = deviceRepository.findByUserId(userId);
        return devices.stream().map(DeviceResponseDto::new).collect(Collectors.toList());
    }

    private Device findDeviceOrThrow(Long userId, Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device with id: " + deviceId + " not found"));

        if (!Objects.equals(userId, device.getUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return device;
    }
}
