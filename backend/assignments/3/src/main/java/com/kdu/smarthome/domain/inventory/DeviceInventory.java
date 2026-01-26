package com.kdu.smarthome.domain.inventory;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.kdu.smarthome.domain.Audit;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "device_inventory") 
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE device_inventory SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE id = ?") 
public class DeviceInventory extends Audit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "kickston_id", length=6, unique = true, nullable = false)
    private String kickstonId;

    @Column(name = "device_username",nullable = false)
    private String deviceUsername;

    @Column(name = "device_password", nullable = false)
    private String devicePassword;

    @Column(name = "manufacture_date_time", nullable = false)
    private Instant manufactureDateTime;

    @Column(name = "manufacture_factory_place", nullable = false)
    private String manufactureFactoryPlace;

    @Column(name = "registered_id", nullable = true)
    @JoinColumn(name = "registered_id")
    private UUID registeredId;

    @PrePersist
    void generateHexId() {
        int value = ThreadLocalRandom.current().nextInt(1, 0x1000000);
        this.kickstonId  = String.format("%06X", value);
    }


}
