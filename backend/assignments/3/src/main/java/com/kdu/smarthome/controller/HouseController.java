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

import com.kdu.smarthome.domain.HouseUserBridge;
import com.kdu.smarthome.domain.house.House;
import com.kdu.smarthome.dto.request.AddUserToHouseRequest;
import com.kdu.smarthome.dto.request.CreateHouseRequest;
import com.kdu.smarthome.services.HouseService;

@RestController
@RequestMapping(ApiEndpoints.House.BASE)
public class HouseController {
    
    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
    
    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping(ApiEndpoints.House.GET_MY_HOUSES)
    public ResponseEntity<List<House>> getMyHouses(){
        logger.info("Fetching houses for authenticated user");
        
        List<House> houses = houseService.getHousesByUser();
        
        return ResponseEntity.ok(houses);
    }

    @PostMapping
    public ResponseEntity<House> createHouse(

        @Validated @RequestBody CreateHouseRequest request
    ){
        
        House house = houseService.createHouse( request.getAddress() );
        logger.info("House created with ID: {}", house.getHouseId());
        return ResponseEntity.ok(house);
    }

    @PutMapping(ApiEndpoints.House.UPDATE_ADDRESS)
    public ResponseEntity<House> updateAddress(@PathVariable UUID houseId, @Validated @RequestBody CreateHouseRequest request){
        
        House updatedHouse = houseService.updateAddress(houseId, request.getAddress());
        
        return ResponseEntity.ok(updatedHouse);
    }

    @PutMapping(ApiEndpoints.House.TRANSFER_OWNER)
    public ResponseEntity<HouseUserBridge> transferOwnership(@PathVariable UUID houseId, @Validated @RequestBody UUID newAdminId){
        
        HouseUserBridge bridge = houseService.transferOwnership(houseId, newAdminId);
        
        return ResponseEntity.ok(bridge);

    }

    @PostMapping(ApiEndpoints.House.ADD_USER_TO_HOUSE)
    public ResponseEntity<HouseUserBridge> addUserToHouse(
        @PathVariable UUID houseId,
        @Validated @RequestBody AddUserToHouseRequest request
    ){
        logger.info("Adding user {} to house {}", request.getUsername(), houseId);
        
        return ResponseEntity.ok(houseService.addUserToHouse(houseId, request.getUsername()));
    }

    @DeleteMapping(ApiEndpoints.House.DELETE_USER_FROM_HOUSE)
    public ResponseEntity<String> deleteUserFromHouse(
        @PathVariable UUID houseId,
        @PathVariable UUID userId
    ){
        logger.info("Removing user {} from house {}", userId, houseId);
        
        
        String responseMessage = houseService.deleteUserFromHouse(houseId, userId);
        return ResponseEntity.ok(responseMessage);
    }

    @DeleteMapping(ApiEndpoints.House.DELETE_HOUSE)
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID houseId){
        logger.info("Deleting house {}", houseId);
        
        houseService.deleteHouse(houseId);
        
        return ResponseEntity.noContent().build();
    }
}
