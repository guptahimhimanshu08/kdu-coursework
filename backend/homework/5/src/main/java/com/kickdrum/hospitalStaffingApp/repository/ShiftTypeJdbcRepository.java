package com.kickdrum.hospitalStaffingApp.repository;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kickdrum.hospitalStaffingApp.model.ShiftType;

@Repository
public class ShiftTypeJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftTypeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ShiftType shiftType) {
        jdbcTemplate.update("""
            INSERT INTO shift_type (id, name, description, active_status, tenant_id)
            VALUES (?, ?, ?, ?, ?)
        """,
            UUID.randomUUID(),
            shiftType.getName(),
            shiftType.getDescription(),
            shiftType.isActiveStatus(),
            shiftType.getTenantId()
        );
    }
}
