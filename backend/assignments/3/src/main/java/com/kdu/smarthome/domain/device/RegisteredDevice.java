package com.kdu.smarthome.domain.device;

import java.util.UUID;

import com.kdu.smarthome.domain.Audit;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "registered_devices")
@SQLDelete(sql = "UPDATE registered_devices SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE register_id = ?")
public class RegisteredDevice extends Audit{
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID registerId;

    @Column(length=6, nullable = false, unique = true)
    private String kickstonId;

    @Column(nullable = false)
    private UUID houseId;

    @Column(nullable = true)
    private UUID roomId;

    @Version
    private Long version;
}
