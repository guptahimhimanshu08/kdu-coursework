package com.kdu.smarthome.domain;

import java.util.UUID;

import org.hibernate.annotations.SQLDelete;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "house_user_bridge",
    /** Optimistic handling to avoid duplicate entries */
    uniqueConstraints = 
        @UniqueConstraint(columnNames = {"houseId", "userId"})
    
)
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE house_user_bridge SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE id = ?")
public class HouseUserBridge extends Audit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID houseId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String role;

    /** avoid concurrent admin transfer updates */
    @Version
    private Long version;
}
