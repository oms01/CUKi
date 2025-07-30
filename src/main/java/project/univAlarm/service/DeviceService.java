package project.univAlarm.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserDevice;
import project.univAlarm.repository.UserDeviceRepository;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final UserDeviceRepository userDeviceRepository;

    public UserDevice save(UserDevice userDevice){
        return userDeviceRepository.save(userDevice);
    }

    public UserDevice update(UserDevice userDevice){
        return userDeviceRepository.save(userDevice);
    }

    public void delete(UserDevice userDevice){
        userDeviceRepository.delete(userDevice);
    }

    public List<UserDevice> findByUser(User user){
        return userDeviceRepository.findByUserId(user.getId());
    }
}
