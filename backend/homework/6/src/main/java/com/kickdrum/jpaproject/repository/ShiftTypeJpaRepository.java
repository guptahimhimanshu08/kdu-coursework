package com.kickdrum.jpaproject.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kickdrum.jpaproject.model.ShiftType;

public interface ShiftTypeJpaRepository extends JpaRepository<ShiftType, UUID> {
}
