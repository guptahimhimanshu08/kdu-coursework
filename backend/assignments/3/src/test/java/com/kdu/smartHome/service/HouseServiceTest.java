package com.kdu.smarthome.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.kdu.smarthome.domain.HouseUserBridge;
import com.kdu.smarthome.domain.HouseUserBridgeRepository;
import com.kdu.smarthome.domain.device.RegisteredDeviceRepository;
import com.kdu.smarthome.domain.house.House;
import com.kdu.smarthome.domain.house.HouseRepository;
import com.kdu.smarthome.domain.room.RoomRepository;
import com.kdu.smarthome.domain.user.User;
import com.kdu.smarthome.domain.user.UserRepository;
import com.kdu.smarthome.exceptions.UserNotFoundException;
import com.kdu.smarthome.services.HouseService;

@ExtendWith(MockitoExtension.class)
class HouseServiceTest {
    
    @Mock
    private HouseRepository houseRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private HouseUserBridgeRepository houseUserRepository;
    
    @Mock
    private RegisteredDeviceRepository registeredDeviceRepository;
    
    @Mock
    private RoomRepository roomRepository;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @InjectMocks
    private HouseService houseService;
    
    private User mockUser;
    private House house1;
    private House house2;
    private HouseUserBridge bridge1;
    private HouseUserBridge bridge2;
    private List<HouseUserBridge> userBridges;
    private List<UUID> houseIds;
    private List<House> mockHouses;
    
    @BeforeEach
    void setup() {

        mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("testuser");
        
        house1 = new House();
        house1.setHouseId(UUID.randomUUID());
        house1.setAddress("BTM Layout, Bangalore");
        house1.setAdminId(mockUser.getId());
        
        house2 = new House();
        house2.setHouseId(UUID.randomUUID());
        house2.setAddress("HSR Layout, Bangalore");
        house2.setAdminId(UUID.randomUUID()); 
        
        bridge1 = new HouseUserBridge();
        bridge1.setUserId(mockUser.getId());
        bridge1.setHouseId(house1.getHouseId());
        bridge1.setRole("ADMIN");
        
        bridge2 = new HouseUserBridge();
        bridge2.setUserId(mockUser.getId());
        bridge2.setHouseId(house2.getHouseId());
        bridge2.setRole("USER");
        
        userBridges = new ArrayList<>();
        userBridges.add(bridge1);
        userBridges.add(bridge2);
        
        houseIds = List.of(house1.getHouseId(), house2.getHouseId());
        
        mockHouses = new ArrayList<>();
        mockHouses.add(house1);
        mockHouses.add(house2);
        
        SecurityContextHolder.setContext(securityContext);
    }
    
    @Test
    void shouldGetHousesByUser() {
        /** Arrange: Mock the security context and repository calls */
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser.getUsername());
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(houseUserRepository.findByUserId(mockUser.getId())).thenReturn(userBridges);
        when(houseRepository.findAllById(houseIds)).thenReturn(mockHouses);
        
        /** Act: Call the service method */
        List<House> result = houseService.getHousesByUser();
        
        /** Assert: Verify the results */
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "Should return 2 houses");
        assertEquals(house1.getHouseId(), result.get(0).getHouseId(), "First house should match");
        assertEquals(house2.getHouseId(), result.get(1).getHouseId(), "Second house should match");
        assertEquals("BTM Layout, Bangalore", result.get(0).getAddress(), "First house address should match");
        assertEquals("HSR Layout, Bangalore", result.get(1).getAddress(), "Second house address should match");
        
        /** Verify all repository methods were called correctly */
        verify(userRepository, times(1)).findByUsername(mockUser.getUsername());
        verify(houseUserRepository, times(1)).findByUserId(mockUser.getId());
        verify(houseRepository, times(1)).findAllById(houseIds);
    }
    
    @Test
    void shouldthrowUserNotFoundException() {

        /** Arrange: Mock user not found in database */
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("nonexistentuser");
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
        
        /** Act & Assert: Expect UserNotFoundException */
        assertThrows(UserNotFoundException.class, () -> {
            houseService.getHousesByUser();
        }, "Should throw UserNotFoundException when user doesn't exist");
        
        /** Verify only user lookup was attempted */
        verify(userRepository, times(1)).findByUsername("nonexistentuser");
        verify(houseUserRepository, times(0)).findByUserId(mockUser.getId());
    }
    
    @Test
    void shouldReturnEmptyListWhenUserHasNoHouses() {
        /** Arrange: User exists but has no houses */
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser.getUsername());
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));
        when(houseUserRepository.findByUserId(mockUser.getId())).thenReturn(List.of()); 
        
        /** Act: Call the service method */
        List<House> result = houseService.getHousesByUser();
        
        /** Assert: Should return empty list */
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty when user has no houses");
        
        /** Verify repositories were called but findAllById was not */
        verify(userRepository, times(1)).findByUsername(mockUser.getUsername());
        verify(houseUserRepository, times(1)).findByUserId(mockUser.getId());
        verify(houseRepository, times(0)).findAllById(houseIds);
    }
        
}
