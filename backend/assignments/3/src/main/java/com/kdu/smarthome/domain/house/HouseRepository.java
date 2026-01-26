package com.kdu.smarthome.domain.house;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kdu.smarthome.dto.request.DeviceDto;

@Repository
public interface HouseRepository extends JpaRepository<House, UUID> {
    
    @Query("""
            SELECT new com.kdu.smarthome.dto.request.DeviceDto( r.id, d.id ) from Room r left join RegisteredDevice d on d.roomId = r.id
            WHERE r.houseId = :houseId
            """)
    List<DeviceDto> findAllDevicesInAllRooms(UUID houseId);
}
