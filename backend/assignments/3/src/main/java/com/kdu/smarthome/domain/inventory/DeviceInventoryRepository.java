package com.kdu.smarthome.domain.inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInventoryRepository extends JpaRepository<DeviceInventory, Long> {
    Optional<DeviceInventory>findByKickstonId(String kickstonId);
}
