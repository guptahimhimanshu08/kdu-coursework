package com.kickdrum.hospitalStaffingApp.repository;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kickdrum.hospitalStaffingApp.model.ShiftUser;

@Repository
public class ShiftUserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftUserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ShiftUser su) {
        jdbcTemplate.update("""
            INSERT INTO shift_user (id, shift_id, user_id, tenant_id)
            VALUES (?, ?, ?, ?)
        """,
            UUID.randomUUID(),
            su.getShiftId(),
            su.getUserId(),
            su.getTenantId()
        );
    }
}
