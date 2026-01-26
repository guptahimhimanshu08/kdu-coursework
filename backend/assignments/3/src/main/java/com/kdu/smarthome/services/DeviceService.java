package com.kdu.smarthome.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kdu.smarthome.domain.HouseUserBridgeRepository;
import com.kdu.smarthome.domain.device.RegisteredDevice;
import com.kdu.smarthome.domain.device.RegisteredDeviceRepository;
import com.kdu.smarthome.domain.house.House;
import com.kdu.smarthome.domain.house.HouseRepository;
import com.kdu.smarthome.domain.inventory.DeviceInventory;
import com.kdu.smarthome.domain.inventory.DeviceInventoryRepository;
import com.kdu.smarthome.domain.room.Room;
import com.kdu.smarthome.domain.room.RoomRepository;
import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.domain.user.UserRepository;
import com.kdu.smarthome.dto.request.DeviceDto;
import com.kdu.smarthome.dto.response.RoomDeviceResponse;
import com.kdu.smarthome.exceptions.*;

import jakarta.transaction.Transactional;

@Service
public class DeviceService {

    private static final String HOUSE_NOT_FOUND = "House not found";
    private static final String DEVICE_NOT_FOUND = "Device not found";
    private static final String ROOM_NOT_FOUND = "Room not found";
    private static final String ADMIN_NOT_FOUND = "Admin not found";

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    private final RegisteredDeviceRepository registeredDeviceRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final DeviceInventoryRepository deviceInventoryRepository;
    private final HouseUserBridgeRepository houseUserBridgeRepository;  

    public DeviceService(RegisteredDeviceRepository registeredDeviceRepository, UserRepository userRepository, 
        HouseRepository houseRepository, RoomRepository roomRepository, DeviceInventoryRepository deviceInventoryRepository, HouseUserBridgeRepository houseUserBridgeRepository) {
        this.registeredDeviceRepository = registeredDeviceRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.deviceInventoryRepository = deviceInventoryRepository;
        this.houseUserBridgeRepository = houseUserBridgeRepository;
    }

    @Transactional
    public RegisteredDevice addDeviceToHouse(UUID houseId, String kickstonId, String deviceUsername, String devicePassword){
        logger.info("Attempting to register device {} to house {}", kickstonId, houseId);
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }

        if(kickstonId == null || kickstonId.isEmpty()){
            throw new IllegalArgumentException("Kickston ID cannot be null or empty");
        }
        /**
         * Ensure each device from the inventory is registered to only one house at a time
        */
        if(registeredDeviceRepository.findByKickstonId(kickstonId).isPresent()){
            logger.warn("Device {} is already registered to another house", kickstonId);
            throw new DeviceAlreadyRegisteredException("Device with Kickston ID " + kickstonId + " is already registered to a house");
        }

        /**
        * find out if person with this username is admin of the current house passed as houseId
        */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();
        User principalUser = userRepository.findByUsername(principalUsername).orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));        
        
        if(!house.getAdminId().equals(principalUser.getId())){
            throw new UnauthorizedAccessException("Only admin can add users to house");
        }

        DeviceInventory deviceInventory = deviceInventoryRepository.findByKickstonId(kickstonId).orElseThrow(() -> new DeviceInventoryNotFoundException("Device not found in inventory"));

        if(!deviceInventory.getDeviceUsername().equals(deviceUsername) || !deviceInventory.getDevicePassword().equals(devicePassword)){
            logger.warn("Device credential mismatch for device {}", kickstonId);
            throw new DeviceCredentialMismatchException("Device credentials do not match inventory records");
        }

        RegisteredDevice device = new RegisteredDevice();
        device.setHouseId(houseId);
        device.setKickstonId(kickstonId);   

        RegisteredDevice savedDevice = registeredDeviceRepository.save(device);
        logger.info("Device {} successfully registered to house {}", kickstonId, houseId);
        return savedDevice;
    }

    @Transactional
    public RegisteredDevice addDeviceToRoom(UUID roomId, UUID deviceRegistrationId){
        logger.info("Attempting to assign device {} to room {}", deviceRegistrationId, roomId);
        
        if(deviceRegistrationId == null || roomId == null){
            throw new IllegalArgumentException("Device Registration ID and Room ID cannot be null");
        }
        
        /**
         * Ensure device is not already registered to a room
        */
        
        RegisteredDevice device = registeredDeviceRepository.findById(deviceRegistrationId).orElseThrow(() -> new RegisteredDeviceNotFoundException(DEVICE_NOT_FOUND));

        UUID fetchRoomId = device.getRoomId(); 

        if(fetchRoomId != null){
            logger.warn("Device {} is already assigned to room {}", deviceRegistrationId, fetchRoomId);
            throw new DeviceAlreadyAssignedToRoomException("This device is already added to the room " + fetchRoomId);
        }

        /** validate that device belongs to the same hosue as the room */
        
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(ROOM_NOT_FOUND));
        
        if(!device.getHouseId().toString().equals(room.getHouseId().toString())){
            logger.warn("Device {} does not belong to the same house as room {}", deviceRegistrationId, roomId);
            throw new DeviceHouseMismatchException("Device does not belong to the same house as the room");
        }

        device.setRoomId(roomId);

        RegisteredDevice savedDevice = registeredDeviceRepository.save(device);
        logger.info("Device {} successfully assigned to room {}", deviceRegistrationId, roomId);
        return savedDevice;
    }

    @Transactional
    public List<RoomDeviceResponse> getAllDevicesInAllRooms(UUID houseId){
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }

        /** check if the user belongs to this house */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();
        
        User principalUser = userRepository.findByUsername(principalUsername).orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        boolean exists = houseUserBridgeRepository.existsByHouseIdAndUserId(houseId, principalUser.getId());

        if (!exists) {
            throw new UserNotFoundException("User does not belong to this house");
        }


        /** fetch all devices in all rooms for a given house
         */

        List<DeviceDto> devices = houseRepository.findAllDevicesInAllRooms(houseId);
       
        Map<UUID, Set<UUID>> roomDeviceMap = devices.stream()
             .collect(Collectors.groupingBy(DeviceDto::getRoomId,
                Collectors.mapping(
                    DeviceDto::getDeviceId,
                    Collectors.toSet()
                )
             ));
        
        return 
            roomDeviceMap.entrySet()
                .stream()
                .map(entry -> 
                    new RoomDeviceResponse(
                        entry.getKey(), entry.getValue()
                    )
                ).toList();

    }

    public RegisteredDevice moveDevice(UUID deviceId, UUID targetRoomId) {
       
        if(deviceId == null || targetRoomId == null){
            throw new IllegalArgumentException("Device ID and Target Room ID cannot be null");
        }

        /** validate that the deviceId and targetRoomID belong to same house */
        RegisteredDevice device = registeredDeviceRepository.findById(deviceId).orElseThrow(() -> new RegisteredDeviceNotFoundException(DEVICE_NOT_FOUND));
        Room targetRoom = roomRepository.findById(targetRoomId).orElseThrow(() -> new RoomNotFoundException("Target room not found"));

        if(!device.getHouseId().toString().equals(targetRoom.getHouseId().toString())){
            logger.warn("Device {} does not belong to the same house as target room {}", deviceId, targetRoomId);
            throw new DeviceHouseMismatchException("Device does not belong to the same house as the target room");
        }

        /** update the device's roomId */
        device.setRoomId(targetRoomId);
        
        logger.info("Device {} successfully moved to room {}", deviceId, targetRoomId);
        
        return registeredDeviceRepository.save(device);

    }

    @Transactional
    public void removeDeviceFromRoom(UUID roomId, UUID deviceId){
        
        if(roomId == null){
            throw new IllegalArgumentException("Room ID cannot be null");
        }

        if(deviceId == null){
            throw new IllegalArgumentException("Device ID cannot be null");
        }

        /** validate that device exists */
        RegisteredDevice device = registeredDeviceRepository.findById(deviceId)
            .orElseThrow(() -> new RegisteredDeviceNotFoundException(DEVICE_NOT_FOUND));

        /** validate that room exists */
        Room room = roomRepository.findById(roomId)
            .orElseThrow(() -> new RoomNotFoundException(ROOM_NOT_FOUND));

        /** validate that device belongs to the specified room */
        if(device.getRoomId() == null || !device.getRoomId().equals(roomId)){
            logger.warn("Device {} is not in room {}", deviceId, roomId);
            throw new IllegalArgumentException("Device is not assigned to this room");
        }

        /** validate that device and room belong to the same house */
        if(!device.getHouseId().equals(room.getHouseId())){
            logger.warn("Device {} does not belong to the same house as room {}", deviceId, roomId);
            throw new DeviceHouseMismatchException("Device does not belong to the same house as the room");
        }

        /** check if the authenticated user is admin of the house */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        User principalUser = userRepository.findByUsername(principalUsername)
            .orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        UUID houseId = room.getHouseId();
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }
        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to remove device from room {}", principalUsername, roomId);
            throw new UnauthorizedAccessException("Only admin can remove devices from rooms");
        }

        /** remove the device from the room by setting roomId to null */
        device.setRoomId(null);
        registeredDeviceRepository.save(device);
        
        logger.info("Device {} successfully removed from room {}", deviceId, roomId);
    }

    @Transactional
    public void unregisterDeviceFromHouse(UUID houseId, UUID deviceId){
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }

        if(deviceId == null){
            throw new IllegalArgumentException("Device ID cannot be null");
        }

        /** validate that device exists */
        RegisteredDevice device = registeredDeviceRepository.findById(deviceId)
            .orElseThrow(() -> new RegisteredDeviceNotFoundException(DEVICE_NOT_FOUND));

        /** validate that house exists */
        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));

        /** validate that device belongs to the specified house */
        if(!device.getHouseId().equals(houseId)){
            logger.warn("Device {} does not belong to house {}", deviceId, houseId);
            throw new DeviceHouseMismatchException("Device is not registered to this house");
        }

        /** check if the authenticated user is admin of the house */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        User principalUser = userRepository.findByUsername(principalUsername)
            .orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to unregister device from house {}", principalUsername, houseId);
            throw new UnauthorizedAccessException("Only admin can unregister devices from house");
        }

        /** Unregister the device*/
        registeredDeviceRepository.delete(device);
        
        logger.info("Device {} successfully unregistered from house {}", deviceId, houseId);
    }
}
