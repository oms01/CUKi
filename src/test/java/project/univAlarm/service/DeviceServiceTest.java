//package project.univAlarm.service;
//
//import java.util.List;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import project.univAlarm.domain.User;
//import project.univAlarm.repository.UserRepository;
//import project.univAlarm.controller.dto.DeviceRequestDto;
//import project.univAlarm.service.dto.DeviceResponseDto;
//import project.univAlarm.service.dto.UserJoinDto;
//
//@SpringBootTest
//class DeviceServiceTest {
//
//    @Autowired
//    private DeviceService deviceService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void saveDevice(){
//
//        User user = createUser("A");
//        DeviceRequestDto deviceRequestDto = createDeviceRequestDto(user, "Galaxy", "token", "Android");
//        deviceService.save(user.getId(), deviceRequestDto);
//
//        List<DeviceResponseDto> devices = deviceService.findAllDevicesByUser(user.getId());
//        Assertions.assertNotNull(devices);
//        Assertions.assertEquals(1, devices.size());
//        Assertions.assertEquals(deviceRequestDto.getUserId(), devices.get(0).getUser().getId());
//    }
//
//    @Test
//    public void deleteDevice(){
//        User user = createUser("A");
//        DeviceRequestDto deviceRequestDto = createDeviceRequestDto(user, "Galaxy", "token", "Android");
//        DeviceResponseDto savedDevice = deviceService.save(user.getId(), deviceRequestDto);
//
//        deviceService.delete(user, savedDevice.getId());
//        List<DeviceResponseDto> devices = deviceService.findAllDevicesByUser(user.getId());
//        Assertions.assertNotNull(devices);
//    }
//
//    @Test
//    public void updateDevice(){
//        User user = createUser("A");
//        DeviceRequestDto deviceRequestDto = createDeviceRequestDto(user, "Galaxy", "token", "Android");
//        DeviceResponseDto savedDevice = deviceService.save(user.getId(), deviceRequestDto);
//
//        DeviceRequestDto updatedDeviceDto = createDeviceRequestDto(user, "myDevice", "updatedToken", "Android");
//        deviceService.update(user, updatedDeviceDto);
//        List<DeviceResponseDto> devices = deviceService.findAllDevicesByUser(user.getId());
//        Assertions.assertEquals(1, devices.size());
//        DeviceResponseDto device = devices.get(0);
//
//        Assertions.assertEquals(device.getUser().getId(), savedDevice.getUser().getId());
//        Assertions.assertEquals(device.getToken(), updatedDeviceDto.getToken());
//        Assertions.assertEquals(device.getName(), updatedDeviceDto.getName());
//    }
//
//    @Test
//    public void getAllDevice(){
//
//        User user = createUser("A");
//        DeviceRequestDto deviceRequestDto1 = createDeviceRequestDto(user, "Galaxy25", "token", "Android");
//        DeviceRequestDto deviceRequestDto2 = createDeviceRequestDto(user, "GalaxyTab", "token", "Android");
//        DeviceRequestDto deviceRequestDto3 = createDeviceRequestDto(user, "Galaxy", "token", "Android");
//
//        deviceService.save(deviceRequestDto1);
//        deviceService.save(deviceRequestDto2);
//        deviceService.save(deviceRequestDto3);
//        List<DeviceResponseDto> devices = deviceService.findAllDevicesByUser(user.getId());
//        Assertions.assertNotNull(devices);
//        Assertions.assertEquals(3, devices.size());
//    }
//
//    private User createUser(String name){
//        UserJoinDto userJoinDto = new UserJoinDto();
//        userJoinDto.setKakaoId(1L);
//        userJoinDto.setEmail("test@test.com");
//        userJoinDto.setUsername(name);
//        User user = new User(userJoinDto);
//        return userRepository.save(user);
//    }
//
//    private DeviceRequestDto createDeviceRequestDto(User user, String deviceName, String token, String platform){
//        return DeviceRequestDto.builder()
//                .user(user)
//                .name(deviceName)
//                .token(token)
//                .platform(platform)
//                .build();
//    }
//}