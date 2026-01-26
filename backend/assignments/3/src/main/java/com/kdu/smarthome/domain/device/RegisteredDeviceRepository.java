package com.kdu.smarthome.domain.device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RegisteredDeviceRepository extends JpaRepository<RegisteredDevice, UUID> {
    
    Optional<RegisteredDevice> findByKickstonId(String kickstonId);
    
    List<RegisteredDevice> findByRoomId(UUID roomId);
    
    List<RegisteredDevice> findByHouseId(UUID houseId);
    
    // ========== Soft Delete Related Native Queries ==========
    
    /**
     * Find all devices including soft-deleted ones
     */
    @Query(value = "SELECT * FROM registered_devices WHERE house_id = :houseId", nativeQuery = true)
    List<RegisteredDevice> findByHouseIdIncludingDeleted(@Param("houseId") UUID houseId);
    
}

