package project.univAlarm.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserDevice;
import project.univAlarm.repository.UserDeviceRepository;
import project.univAlarm.service.dto.DeviceRequestDto;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final UserDeviceRepository userDeviceRepository;

    public UserDevice save(DeviceRequestDto userDevice){
        return userDeviceRepository.save(new UserDevice(userDevice));
    }

    public UserDevice update(User user, Long deviceId, DeviceRequestDto userDevice){
        UserDevice device = findDeviceOrThrow(user, deviceId);
        device.updateDeviceInfo(userDevice);
        return userDeviceRepository.save(device);
    }

    public void delete(User user, Long deviceId){
        UserDevice device = findDeviceOrThrow(user, deviceId);
        userDeviceRepository.deleteById(deviceId);
    }

    public List<UserDevice> findAllDevicesByUser(User user){
        return userDeviceRepository.findByUserId(user.getId());
    }

    private UserDevice findDeviceOrThrow(User user, Long deviceId) {
        UserDevice device = userDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("Device with id: " + deviceId + " not found"));

        if (!Objects.equals(user.getId(), device.getUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }

        return device;
    }
}
