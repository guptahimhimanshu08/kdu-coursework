package com.kdu.smarthome.domain.room;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    
    Page<Room> findByHouseId(UUID houseId, Pageable pageable);
    
    List<Room> findByHouseId(UUID houseId);
    
    // ========== Soft Delete Related Native Queries ==========
    
    /**
     * Find all rooms including soft-deleted ones
     */
    @Query(value = "SELECT * FROM rooms WHERE house_id = :houseId", nativeQuery = true)
    List<Room> findByHouseIdIncludingDeleted(@Param("houseId") UUID houseId);
    
    
}
