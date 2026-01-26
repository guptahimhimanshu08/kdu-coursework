package com.kdu.smarthome.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.kdu.smarthome.domain.house.House;
import com.kdu.smarthome.services.HouseService;

@WebMvcTest(HouseController.class)
class HouseControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HouseService houseService;

    private List<House> mockHouses;
    private House house1;

    @BeforeEach
    void setup() {

        house1 = new House();
        house1.setHouseId(UUID.randomUUID());
        house1.setAddress("123 Main Street");
        house1.setAdminId(UUID.randomUUID());
        
        mockHouses = new ArrayList<>();
        mockHouses.add(house1);

    }

    @Test
    void testGetMyHouses() throws Exception {
        
        /** Arrange: Mock the service to return a list of houses */
        when(houseService.getHousesByUser()).thenReturn(mockHouses);
        
        /** Act: Perform GET request to the endpoint and Assert the response */
        mockMvc.perform(get("/api/v1/houses/myHouses"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].houseId").value(house1.getHouseId().toString()))
               .andExpect(jsonPath("$[0].address").value("123 Main Street"))
               .andReturn();

        /** Assert: Verify the service method was called exactly once */
        verify(houseService, times(1)).getHousesByUser();
        
    }
}
