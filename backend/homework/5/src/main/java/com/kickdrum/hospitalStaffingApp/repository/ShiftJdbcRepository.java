package com.kickdrum.hospitalStaffingApp.repository;

import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kickdrum.hospitalStaffingApp.model.Shift;

@Repository
public class ShiftJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShiftJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Shift shift) {
        jdbcTemplate.update("""
            INSERT INTO shift (id, shift_type_id, start_date, end_date, start_time, end_time, tenant_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """,
            UUID.randomUUID(),
            shift.getShiftTypeId(),
            shift.getStartDate(),
            shift.getEndDate(),
            shift.getStartTime(),
            shift.getEndTime(),
            shift.getTenantId()
        );
    }
}
