package com.kdu.smarthome.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.kdu.smarthome.domain.device.RegisteredDevice;
import com.kdu.smarthome.services.DeviceService;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    private UUID deviceId;
    private UUID targetRoomId;
    private UUID houseId;
    private RegisteredDevice mockDevice;

    @BeforeEach
    void setup() {
        deviceId = UUID.randomUUID();
        targetRoomId = UUID.randomUUID();
        houseId = UUID.randomUUID();
        
        mockDevice = new RegisteredDevice();
        mockDevice.setRegisterId(deviceId);
        mockDevice.setRoomId(targetRoomId);
        mockDevice.setHouseId(houseId);
        mockDevice.setKickstonId("ABC123");
    }

    @Test
    void testMoveDeviceToRoom() throws Exception {
        /** Arrange: Mock the service to return a RegisteredDevice when moveDevice is called */
        when(deviceService.moveDevice(deviceId, targetRoomId)).thenReturn(mockDevice);
        
        /** Act: Perform PUT request to the endpoint and Assert the response */
        mockMvc.perform(put("/api/v1/houses/devices/{deviceId}/rooms/{newRoomId}", deviceId, targetRoomId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.registerId").value(deviceId.toString()))
               .andExpect(jsonPath("$.roomId").value(targetRoomId.toString()))
               .andExpect(jsonPath("$.houseId").value(houseId.toString()))
               .andExpect(jsonPath("$.kickstonId").value("ABC123"))
               .andReturn();

        /** Assert: Verify the service method was called exactly once */
        verify(deviceService, times(1)).moveDevice(deviceId, targetRoomId);
    }
}
