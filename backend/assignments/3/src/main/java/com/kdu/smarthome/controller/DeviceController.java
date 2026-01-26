package com.kdu.smarthome.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdu.smarthome.domain.device.RegisteredDevice;
import com.kdu.smarthome.dto.request.RegisterDeviceRequest;
import com.kdu.smarthome.dto.response.RoomDeviceResponse;
import com.kdu.smarthome.services.DeviceService;

@RestController
@RequestMapping("/api/v1/houses")
public class DeviceController {
    
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    private final DeviceService deviceService;
    
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(ApiEndpoints.House.ADD_DEVICE_HOUSE)
    public ResponseEntity<RegisteredDevice> addDeviceToHouse(
        @Validated @PathVariable UUID houseId,
        @Validated @RequestBody RegisterDeviceRequest request
    ) {
        RegisteredDevice device = deviceService.addDeviceToHouse(houseId, request.getKickstonId(), request.getDeviceUsername(), request.getDevicePassword());
        
        logger.info("Device added: {}", device.getRegisterId());

        return ResponseEntity.ok(device);
    }

    @PutMapping(ApiEndpoints.House.ADD_DEVICE_ROOM)
    public ResponseEntity<RegisteredDevice> addDeviceToRoom(
        @Validated @PathVariable UUID roomId,
        @Validated @PathVariable UUID deviceId
    ) {
        RegisteredDevice device = deviceService.addDeviceToRoom(roomId, deviceId);
        logger.info("Device added to room: {}", device.getRegisterId());
        return ResponseEntity.ok(device);
    }

    @GetMapping(ApiEndpoints.House.GET_DEVICE_ALL_ROOMS)
    public ResponseEntity<List<RoomDeviceResponse>> getAllDevicesInAllRooms(
        @Validated @PathVariable UUID houseId
    ){
        logger.info("Fetching all devices for house {}", houseId);
        List<RoomDeviceResponse> devices = deviceService.getAllDevicesInAllRooms(houseId);
        return ResponseEntity.ok(devices);
    }

    @PutMapping(ApiEndpoints.House.MOVE_DEVICE)
    public ResponseEntity<RegisteredDevice> moveDeviceToAnotherRoom(
        @Validated @PathVariable UUID deviceId,
        @Validated @PathVariable UUID newRoomId
    ){
        
        RegisteredDevice device = deviceService.moveDevice(deviceId, newRoomId);
        
        return ResponseEntity.ok(device);
    }

    @DeleteMapping(ApiEndpoints.House.REMOVE_DEVICE_FROM_ROOM)
    public ResponseEntity<Void> removeDeviceFromRoom(
        @Validated @PathVariable UUID roomId,
        @Validated @PathVariable UUID deviceId
    ){
        logger.info("Removing device {} from room {}", deviceId, roomId);
        
        deviceService.removeDeviceFromRoom(roomId, deviceId);
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ApiEndpoints.House.UNREGISTER_DEVICE)
    public ResponseEntity<Void> unregisterDeviceFromHouse(
        @Validated @PathVariable UUID houseId,
        @Validated @PathVariable UUID deviceId
    ){
        logger.info("Unregistering device {} from house {}", deviceId, houseId);
        
        deviceService.unregisterDeviceFromHouse(houseId, deviceId);
        
        return ResponseEntity.noContent().build();
    }
}
