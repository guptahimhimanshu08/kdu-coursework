package com.kdu.smarthome.domain.house;

import java.util.UUID;

import com.kdu.smarthome.domain.Audit;
import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE house SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE house_id = ?")
public class House extends Audit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID houseId;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private UUID adminId;

    @Version
    private Long version;

}
