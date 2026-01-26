package com.kdu.smarthome;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import com.kdu.smarthome.domain.HouseUserBridge;
import com.kdu.smarthome.domain.HouseUserBridgeRepository;
import com.kdu.smarthome.domain.device.RegisteredDevice;
import com.kdu.smarthome.domain.device.RegisteredDeviceRepository;
import com.kdu.smarthome.domain.house.House;
import com.kdu.smarthome.domain.house.HouseRepository;
import com.kdu.smarthome.domain.room.Room;
import com.kdu.smarthome.domain.room.RoomRepository;
import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.domain.user.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@SuppressWarnings("null")
class ListRoomsDevicesIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RegisteredDeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseUserBridgeRepository houseUserBridgeRepository;

    @Test
    @WithMockUser(username = "testuser")
    void shouldReturnRoomsWithTheirDevicesForHouse() throws Exception {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("hashedpassword");
        user = userRepository.save(user);

        House house = new House();
        house.setAddress("Test Address");
        house.setAdminId(user.getId());
        house = houseRepository.save(house);

        HouseUserBridge bridge = new HouseUserBridge();
        bridge.setHouseId(house.getHouseId());
        bridge.setUserId(user.getId());
        houseUserBridgeRepository.save(bridge);

        Room room1 = new Room();
        room1.setHouseId(house.getHouseId());
        room1 = roomRepository.save(room1);

        Room room2 = new Room();
        room2.setHouseId(house.getHouseId());
        room2 = roomRepository.save(room2);

        RegisteredDevice device1 = new RegisteredDevice();
        device1.setKickstonId("KID01");
        device1.setHouseId(house.getHouseId());
        device1.setRoomId(room1.getRoomId());
        device1 = deviceRepository.save(device1);

        RegisteredDevice device2 = new RegisteredDevice();
        device2.setKickstonId("KID02");
        device2.setHouseId(house.getHouseId());
        device2.setRoomId(room1.getRoomId());
        device2 = deviceRepository.save(device2);

        RegisteredDevice device3 = new RegisteredDevice();
        device3.setKickstonId("KID03");
        device3.setHouseId(house.getHouseId());
        device3.setRoomId(room2.getRoomId());
        device3 = deviceRepository.save(device3);

        /** Call and Assertions */
        mockMvc.perform(
                get("/api/v1/houses/{houseId}/devices", house.getHouseId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))  
        .andExpect(jsonPath("$[*].roomId",
                containsInAnyOrder(
                        room1.getRoomId().toString(),
                        room2.getRoomId().toString()
                )))
        .andExpect(jsonPath("$[?(@.roomId=='" + room1.getRoomId() + "')].deviceIds", hasSize(2)))
        .andExpect(jsonPath("$[?(@.roomId=='" + room2.getRoomId() + "')].deviceIds", hasSize(1)))
        .andExpect(jsonPath("$[*].deviceIds[*]",
                containsInAnyOrder(
                        device1.getRegisterId().toString(),
                        device2.getRegisterId().toString(),
                        device3.getRegisterId().toString()
                )));
    }
}