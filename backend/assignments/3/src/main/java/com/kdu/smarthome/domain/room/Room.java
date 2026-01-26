package com.kdu.smarthome.domain.room;

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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "rooms")
@SQLDelete(sql = "UPDATE rooms SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE room_id = ?")
public class Room extends Audit{
    

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID roomId;

    @Column(nullable = false)
    private UUID houseId;

    @Version
    private Long version;
}
