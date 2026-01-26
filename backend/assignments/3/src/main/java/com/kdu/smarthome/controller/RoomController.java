package com.kdu.smarthome.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdu.smarthome.domain.room.Room;
import com.kdu.smarthome.services.RoomService;

@RestController
@RequestMapping(ApiEndpoints.House.BASE)
public class RoomController {
    
    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(ApiEndpoints.House.CREATE_ROOM)
    public ResponseEntity<Room> createRoom(
        @PathVariable UUID houseId
    ) {

        Room room = roomService.createRoom(houseId);
        logger.info("Room created with ID: {}", room.getRoomId());
        return ResponseEntity.ok(room);
    }

    @GetMapping(ApiEndpoints.House.GET_ALL_ROOMS)
    public ResponseEntity<Page<Room>> getAllRooms(
            @PathVariable UUID houseId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(roomService.getAllRoomsByHouseId(houseId, pageable));
    }

}
