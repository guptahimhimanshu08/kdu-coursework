package com.kdu.smarthome.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kdu.smarthome.domain.house.House;
import com.kdu.smarthome.domain.house.HouseRepository;
import com.kdu.smarthome.domain.room.Room;
import com.kdu.smarthome.domain.room.RoomRepository;
import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.domain.user.UserRepository;
import com.kdu.smarthome.exceptions.HouseNotFoundException;
import com.kdu.smarthome.exceptions.UnauthorizedAccessException;
import com.kdu.smarthome.exceptions.UserNotFoundException;

@Service
public class RoomService {
    private static final String HOUSE_NOT_FOUND = "House not found";
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository, HouseRepository houseRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }

    public Room createRoom(UUID houseId){
        logger.info("Creating new room in house {}", houseId);
        
        if(houseId == null){
            throw new IllegalArgumentException("House ID cannot be null");
        }
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principalUsername = (String) auth.getPrincipal();

        /**
         * find out if person with this username is admin of the current house passed as houseId
         */
        User principalUser = userRepository.findByUsername(principalUsername).orElseThrow(() -> new UserNotFoundException("Admin not found"));

        House house = houseRepository.findById(houseId).orElseThrow(() -> new HouseNotFoundException(HOUSE_NOT_FOUND));        

        if(!house.getAdminId().equals(principalUser.getId())){
            logger.warn("Unauthorized attempt by user {} to create room in house {}", principalUsername, houseId);
            throw new UnauthorizedAccessException("Only admin can add users to house");
        }

        Room room =  new Room();

        room.setHouseId(houseId);

        Room savedRoom = roomRepository.save(room);
        logger.info("Room {} created successfully in house {}", savedRoom.getRoomId(), houseId);
        return savedRoom;
    }

    public Page<Room> getAllRoomsByHouseId(UUID houseId, Pageable pageable){
        
        return roomRepository.findByHouseId(houseId, pageable);
    
    }
}
