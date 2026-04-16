package project.univAlarm.entity.device.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.univAlarm.entity.device.dto.DeviceRequestDto;
import project.univAlarm.entity.device.dto.UpdateDeviceRequestDto;
import project.univAlarm.entity.device.domain.Device;
import project.univAlarm.entity.user.domain.User;
import project.univAlarm.entity.device.repository.DeviceRepository;
import project.univAlarm.entity.user.repository.UserRepository;
import project.univAlarm.entity.device.dto.DeviceResponseDto;

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
        if(device==null){
            return null;
        }
        device.updateDeviceInfo(userDevice);
        return new DeviceResponseDto(device);
    }

    @Transactional
    public void delete(Long userId, Long deviceId){
        deviceRepository.findById(deviceId).ifPresent(device -> {
            if (!userId.equals(device.getUser().getId())) {
                throw new AccessDeniedException("Access denied");
            }
            deviceRepository.delete(device);
        });
    }

    @Transactional(readOnly = true)
    public List<DeviceResponseDto> findAllDevicesByUser(Long userId){
        List<Device> devices = deviceRepository.findByUserId(userId);
        return devices.stream().map(DeviceResponseDto::new).collect(Collectors.toList());
    }

    private Device findDeviceOrThrow(Long userId, Long deviceId) {
        Optional<Device> device = deviceRepository.findById(deviceId);
        if(device.isEmpty()){
            return null;
        }
        if (!Objects.equals(userId, device.get().getUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return device.get();
    }
}
