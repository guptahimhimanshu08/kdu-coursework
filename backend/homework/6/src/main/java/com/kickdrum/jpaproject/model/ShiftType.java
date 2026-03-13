package com.kickdrum.jpaproject.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shift_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftType {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private boolean activeStatus;

    @Column(nullable = false)
    private UUID tenantId;

    @OneToMany(mappedBy = "shiftType")
    private List<Shift> shifts;
}
