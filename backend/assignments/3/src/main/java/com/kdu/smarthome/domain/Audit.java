package com.kdu.smarthome.domain;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PreRemove;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@SQLDelete(sql = "UPDATE #{#entityName} SET deleted = true, deleted_date = CURRENT_TIMESTAMP WHERE id = ? OR house_id = ? OR user_id = ? OR register_id = ? OR kickston_id = ? OR room_id = ?")
@SQLRestriction("deleted = false")
public abstract class Audit {

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdDate;

    private Instant modifiedDate;

    private Instant deletedDate;

    @Column(nullable = false)
    private boolean deleted = false;

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = Instant.now();
    }

    @PreRemove
    public void preRemove() {
        this.deleted = true;
        this.deletedDate = Instant.now();
    }

}
