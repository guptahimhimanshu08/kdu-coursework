package com.kdu.smarthome.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HouseUserBridgeRepository extends JpaRepository<HouseUserBridge, UUID> {
    
    boolean existsByHouseIdAndUserId(UUID houseId, UUID userId);

    HouseUserBridge findByHouseIdAndUserId(UUID houseId, UUID userId);

    List<HouseUserBridge> findByHouseId(UUID houseId);

    List<HouseUserBridge> findByUserId(UUID userId);

}

