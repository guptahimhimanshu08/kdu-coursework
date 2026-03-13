package com.kickdrum.jpaproject.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private boolean loggedIn;

    private String timezone;

    @Column(nullable = false)
    private UUID tenantId;

    @OneToMany(mappedBy = "user")
    private List<ShiftUser> shiftUsers;
}
