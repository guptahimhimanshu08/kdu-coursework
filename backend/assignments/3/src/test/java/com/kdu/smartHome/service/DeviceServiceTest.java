package com.kdu.smarthome.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdu.smarthome.domain.HouseUserBridgeRepository;
import com.kdu.smarthome.domain.device.RegisteredDevice;
import com.kdu.smarthome.domain.device.RegisteredDeviceRepository;
import com.kdu.smarthome.domain.house.HouseRepository;
import com.kdu.smarthome.domain.inventory.DeviceInventoryRepository;
import com.kdu.smarthome.domain.room.Room;
import com.kdu.smarthome.domain.room.RoomRepository;
import com.kdu.smarthome.domain.user.UserRepository;
import com.kdu.smarthome.exceptions.RegisteredDeviceNotFoundException;
import com.kdu.smarthome.exceptions.RoomNotFoundException;
import com.kdu.smarthome.services.DeviceService;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {
    
    @Mock
    private RegisteredDeviceRepository registeredDeviceRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private HouseRepository houseRepository;
    
    @Mock
    private RoomRepository roomRepository;
    
    @Mock
    private DeviceInventoryRepository deviceInventoryRepository;
    
    @Mock
    private HouseUserBridgeRepository houseUserBridgeRepository;
    
    @InjectMocks
    private DeviceService deviceService;
    
    private UUID deviceId;
    private UUID targetRoomId;
    private UUID houseId;
    private RegisteredDevice mockDevice;
    private Room mockTargetRoom;
    
    @BeforeEach
    void setup() {

        deviceId = UUID.randomUUID();
        targetRoomId = UUID.randomUUID();
        houseId = UUID.randomUUID();
        
        mockDevice = new RegisteredDevice();
        mockDevice.setRegisterId(deviceId);
        mockDevice.setHouseId(houseId);
        mockDevice.setRoomId(UUID.randomUUID()); 
        mockDevice.setKickstonId("00001");
        
        mockTargetRoom = new Room();
        mockTargetRoom.setRoomId(targetRoomId);
        mockTargetRoom.setHouseId(houseId); 
    }
    
    @Test
    void shouldMoveDevice() {

        /** Arrange: Set up mock behavior for successful move */
        when(registeredDeviceRepository.findById(deviceId)).thenReturn(Optional.of(mockDevice));
        when(roomRepository.findById(targetRoomId)).thenReturn(Optional.of(mockTargetRoom));
        when(registeredDeviceRepository.save(any(RegisteredDevice.class))).thenReturn(mockDevice);
        
        /** Act: Call the service method */
        RegisteredDevice result = deviceService.moveDevice(deviceId, targetRoomId);
        
        /** Assert: NotNull and Equals assertions */
        assertNotNull(result, "Result should not be null");
        assertEquals(targetRoomId, mockDevice.getRoomId(), "Device should be moved to target room");
        
        /** Verify repository methods were called */
        verify(registeredDeviceRepository, times(1)).findById(deviceId);
        verify(roomRepository, times(1)).findById(targetRoomId);
        verify(registeredDeviceRepository, times(1)).save(mockDevice);
    }
    
    @Test
    void shouldThrowRegisteredDeviceNotFoundException() {
        
        /** Arrange: Mock device not found */
        when(registeredDeviceRepository.findById(deviceId)).thenReturn(Optional.empty());
        
        /** Act & Assert: Expect exception */
        assertThrows(RegisteredDeviceNotFoundException.class, () -> {
            deviceService.moveDevice(deviceId, targetRoomId);
        }, "Should throw RegisteredDeviceNotFoundException when device is not found");
        
        /** Verify repository was called but save was not */
        verify(registeredDeviceRepository, times(1)).findById(deviceId);
        verify(registeredDeviceRepository, never()).save(any());
    }
    
    @Test
    void shouldThrowRoomNotFoundException() {
        /** Arrange: Device exists but target room doesn't */
        when(registeredDeviceRepository.findById(deviceId)).thenReturn(Optional.of(mockDevice));
        when(roomRepository.findById(targetRoomId)).thenReturn(Optional.empty());
        
        /** Act & Assert: Expect exception */
        assertThrows(RoomNotFoundException.class, () -> {
            deviceService.moveDevice(deviceId, targetRoomId);
        }, "Should throw RoomNotFoundException when target room is not found");
        
        /** Verify save was never called */
        verify(registeredDeviceRepository, times(1)).findById(deviceId);
        verify(roomRepository, times(1)).findById(targetRoomId);
        verify(registeredDeviceRepository, never()).save(any());
    }
    
    
    @Test
    void shouldThrowIllegalArgumentExceptionForNullDeviceId() {
        /** Act & Assert: Expect IllegalArgumentException for null deviceId */
        assertThrows(IllegalArgumentException.class, () -> {
            deviceService.moveDevice(null, targetRoomId);
        }, "Should throw IllegalArgumentException when deviceId is null");
        
        /** Verify no repository calls were made */
        verify(registeredDeviceRepository, never()).findById(any());
    }
    
    @Test
    void shouldThrowIllegalArgumentExceptionForNullTargetRoomId() {
        
        /** Act & Assert: Expect IllegalArgumentException for null targetRoomId */
        assertThrows(IllegalArgumentException.class, () -> {
            deviceService.moveDevice(deviceId, null);
        }, "Should throw IllegalArgumentException when targetRoomId is null");
        
        /** Verify no repository calls were made */
        verify(registeredDeviceRepository, never()).findById(any());
    }
}
