package project.univAlarm.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.univAlarm.domain.User;
import project.univAlarm.domain.UserDevice;
import project.univAlarm.repository.UserRepository;
import project.univAlarm.service.dto.DeviceRequestDto;

@SpringBootTest
class DeviceServiceTest {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveDevice(){

        User user = createUser("A");
        DeviceRequestDto deviceRequestDto = createDeviceRequestDto(user, "Galaxy", "token", "Android");
        deviceService.save(deviceRequestDto);

        List<UserDevice> devices = deviceService.findAllDevicesByUser(user);
        Assertions.assertNotNull(devices);
        Assertions.assertEquals(1, devices.size());
        Assertions.assertEquals(deviceRequestDto.getUser().getId(), devices.get(0).getUser().getId());
    }

    @Test
    public void deleteDevice(){
        User user = createUser("A");
        DeviceRequestDto deviceRequestDto = createDeviceRequestDto(user, "Galaxy", "token", "Android");
        UserDevice savedDevice = deviceService.save(deviceRequestDto);

        deviceService.delete(user, savedDevice.getId());
        List<UserDevice> devices = deviceService.findAllDevicesByUser(user);
        Assertions.assertNotNull(devices);
    }

    @Test
    public void updateDevice(){
        User user = createUser("A");
        DeviceRequestDto deviceRequestDto = createDeviceRequestDto(user, "Galaxy", "token", "Android");
        UserDevice savedDevice = deviceService.save(deviceRequestDto);

        DeviceRequestDto updatedDeviceDto = createDeviceRequestDto(user, "myDevice", "updatedToken", "Android");
        deviceService.update(user, savedDevice.getId(), updatedDeviceDto);
        List<UserDevice> devices = deviceService.findAllDevicesByUser(user);
        Assertions.assertEquals(1, devices.size());
        UserDevice device = devices.get(0);

        Assertions.assertEquals(device.getUser().getId(), savedDevice.getUser().getId());
        Assertions.assertEquals(device.getToken(), updatedDeviceDto.getToken());
        Assertions.assertEquals(device.getName(), updatedDeviceDto.getName());
    }

    @Test
    public void getAllDevice(){

        User user = createUser("A");
        DeviceRequestDto deviceRequestDto1 = createDeviceRequestDto(user, "Galaxy25", "token", "Android");
        DeviceRequestDto deviceRequestDto2 = createDeviceRequestDto(user, "GalaxyTab", "token", "Android");
        DeviceRequestDto deviceRequestDto3 = createDeviceRequestDto(user, "Galaxy", "token", "Android");

        deviceService.save(deviceRequestDto1);
        deviceService.save(deviceRequestDto2);
        deviceService.save(deviceRequestDto3);
        List<UserDevice> devices = deviceService.findAllDevicesByUser(user);
        Assertions.assertNotNull(devices);
        Assertions.assertEquals(3, devices.size());
    }

    private User createUser(String name){
        User user = new User(name, name, name);
        return userRepository.save(user);
    }

    private DeviceRequestDto createDeviceRequestDto(User user, String deviceName, String token, String platform){
        return DeviceRequestDto.builder()
                .user(user)
                .name(deviceName)
                .token(token)
                .platform(platform)
                .build();
    }
}