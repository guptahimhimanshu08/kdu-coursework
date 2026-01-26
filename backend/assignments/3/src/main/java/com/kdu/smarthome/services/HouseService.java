package com.kdu.smarthome.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
import com.kdu.smarthome.exceptions.HouseNotFoundException;
import com.kdu.smarthome.exceptions.UnauthorizedAccessException;
import com.kdu.smarthome.exceptions.UserAlreadyInHouseException;
import com.kdu.smarthome.exceptions.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class HouseService {
    
    private static final String HOUSE_NOT_FOUND = "House not found";
    private static final String ADMIN_NOT_FOUND = "Admin not found";
    private static final String USER_NOT_FOUND = "User not found";  

    private static final Logger logger = LoggerFactory.getLogger(HouseService.class);
    
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;
    private final HouseUserBridgeRepository houseUserRepository;
    private final RegisteredDeviceRepository registeredDeviceRepository;
    private final RoomRepository roomRepository;

    public HouseService(HouseRepository houseRepository, UserRepository userRepository, 
                       HouseUserBridgeRepository houseUserRepository,
                       RegisteredDeviceRepository registeredDeviceRepository,
                       RoomRepository roomRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.houseUserRepository = houseUserRepository;
        this.registeredDeviceRepository = registeredDeviceRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public House createHouse(String address){
        
        logger.info("Creating new house at address: {}", address);

        /** get the currently authenticated user */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = (String) auth.getPrincipal();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        House house = new House();

        /** user who creates the house (authenticated user) becomes the admin */

        house.setAdminId(user.getId());
        house.setAddress(address);

        House savedHouse = houseRepository.save(house);

        HouseUserBridge bridge = new HouseUserBridge();

        bridge.setHouseId(savedHouse.getHouseId());
        bridge.setUserId(user.getId());
        bridge.setRole("ADMIN");
        
        houseUserRepository.save(bridge);
        
        logger.info("House created successfully with ID: {} for admin: {}", savedHouse.getHouseId(), username);
        
        return savedHouse;
    }

    @Transactional
    public HouseUserBridge addUserToHouse(UUID houseId, String username){
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }
        
        if(username == null || username.isEmpty()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        
        /** check if this username and houseId relation exists already
         * to avoid duplicate entries
         */
        boolean exists = houseUserRepository.existsByHouseIdAndUserId(houseId, user.getId());

        if (exists) {
            logger.warn("Attempt to add user {} to house {} - user already exists", username, houseId);
            throw new UserAlreadyInHouseException("User already belongs to this house");
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        /**
         * find out if person with this username is admin of the current house passed as houseId
         */

        User principalUser = userRepository.findByUsername(principalUsername).orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));        

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to add user to house {}", principalUsername, houseId);
            throw new UnauthorizedAccessException("Only admin can add users to house");
        }

        HouseUserBridge bridge = new HouseUserBridge();
        
        bridge.setHouseId(houseId);
        bridge.setUserId(user.getId());
        bridge.setRole("USER");

        HouseUserBridge savedBridge = houseUserRepository.save(bridge);
        
        logger.info("User {} successfully added to house {}", username, houseId);
        
        return savedBridge;
    }

    public House updateAddress(UUID houseId, String newAddress){
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }

        House house = houseRepository.findById(houseId).orElseThrow( () -> new HouseNotFoundException(HOUSE_NOT_FOUND));

        house.setAddress(newAddress);

        House updatedHouse = houseRepository.save(house);
        
        logger.info("House {} address updated to {}", houseId, newAddress);

        return updatedHouse;
    }

    @Transactional
    public HouseUserBridge transferOwnership(UUID houseId, UUID newAdminId){
       
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }
        /** check if the authenticated user is admin of the house */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        User principalUser = userRepository.findByUsername(principalUsername).orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));        

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to add user to house {}", principalUsername, houseId);
            throw new UnauthorizedAccessException("Only admin can add users to house");
        }

        /** check if provided newAdminId is the user of the house */
        boolean exists = houseUserRepository.existsByHouseIdAndUserId(houseId, newAdminId);
        
        if(!exists){
            logger.warn("Attempt to transfer ownership of house {} to user {} who is not part of the house", houseId, newAdminId);
            throw new UnauthorizedAccessException("New admin must be a user of the house");
        }
        /** update the house adminId to newAdminId */
        house.setAdminId(newAdminId);
        
        houseRepository.save(house);

        /** update the HouseUserBridge roles accordingly */
        HouseUserBridge currentAdminBridge = houseUserRepository.findByHouseIdAndUserId(houseId, principalUser.getId());
        currentAdminBridge.setRole("USER");
        houseUserRepository.save(currentAdminBridge);

        HouseUserBridge newAdminBridge = houseUserRepository.findByHouseIdAndUserId(houseId, newAdminId);
        newAdminBridge.setRole("ADMIN");
        houseUserRepository.save(newAdminBridge);

        return newAdminBridge;
    }

    @Transactional
    public String deleteUserFromHouse(UUID houseId, UUID userId){
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }

        if(userId == null){
            throw new IllegalArgumentException("User ID cannot be null");
        }

        /** check if the authenticated user is admin of the house */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        User principalUser = userRepository.findByUsername(principalUsername)
            .orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));        

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to delete user from house {}", principalUsername, houseId);
            throw new UnauthorizedAccessException("Only admin can delete users from house");
        }

        /** check if the user being deleted is the admin */
        if(house.getAdminId().equals(userId)){
            logger.warn("Attempt to delete admin user {} from house {}", userId, houseId);
            throw new UnauthorizedAccessException("Cannot delete admin from house. Transfer ownership first.");
        }

        /** verify that user exists in the house */
        HouseUserBridge bridge = houseUserRepository.findByHouseIdAndUserId(houseId, userId);
        
        if(bridge == null){
            logger.warn("Attempt to delete user {} who is not part of house {}", userId, houseId);
            throw new UserNotFoundException("User is not part of this house");
        }

        /** delete the HouseUserBridge entry */
        houseUserRepository.delete(bridge);
        
        logger.info("User {} successfully removed from house {}", userId, houseId);

        return "User removed from house successfully";
    }

    @Transactional
    public void deleteHouse(UUID houseId){
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }

        /** check if the authenticated user is admin of the house */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        User principalUser = userRepository.findByUsername(principalUsername)
            .orElseThrow(() -> new UserNotFoundException(ADMIN_NOT_FOUND));

        House house = houseRepository.findById(houseId)
            .orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to delete house {}", principalUsername, houseId);
            throw new UnauthorizedAccessException("Only admin can delete the house");
        }

        logger.info("Starting deletion process for house {}", houseId);

        /** Unregister all devices associated with this house */
        List<RegisteredDevice> devices = registeredDeviceRepository.findByHouseId(houseId);
        if(!devices.isEmpty()){
            registeredDeviceRepository.deleteAll(devices);
            logger.info("Deleted {} devices from house {}", devices.size(), houseId);
        }

        /** Delete all rooms associated with this house */
        List<Room> rooms = roomRepository.findByHouseId(houseId);
        if(!rooms.isEmpty()){
            roomRepository.deleteAll(rooms);
            logger.info("Deleted {} rooms from house {}", rooms.size(), houseId);
        }

        /** Delete all user associations (HouseUserBridge entries) */
        List<HouseUserBridge> userBridges = houseUserRepository.findByHouseId(houseId);
        if(!userBridges.isEmpty()){
            houseUserRepository.deleteAll(userBridges);
            logger.info("Deleted {} user associations from house {}", userBridges.size(), houseId);
        }

        /** Delete the house itself */
        houseRepository.delete(house);
        
        logger.info("House {} successfully deleted with all associated resources", houseId);
    }

    public List<House> getHousesByUser(){
        
        /** get the currently authenticated user */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        logger.info("Fetching houses for user: {}", username);

        List<HouseUserBridge> userBridges = houseUserRepository.findByUserId(user.getId());

        /** extract list of all house IDs that belong to the user */
        List<UUID> houseIds = userBridges.stream()
            .map(HouseUserBridge::getHouseId)
            .toList();

        if(houseIds.isEmpty()){
            logger.info("No houses found for user {}", username);
            return List.of();
        }

        /** fetch all houses using the list of house IDs */
        List<House> houses = houseRepository.findAllById(houseIds);
        
        logger.info("Found {} houses for user {}", houses.size(), username);

        return houses;
    }
    
}
