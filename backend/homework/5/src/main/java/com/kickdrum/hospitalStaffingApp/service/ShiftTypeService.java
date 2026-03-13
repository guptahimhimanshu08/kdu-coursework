package com.kickdrum.hospitalStaffingApp.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kickdrum.hospitalStaffingApp.dto.CreateShiftTypeRequest;
import com.kickdrum.hospitalStaffingApp.exception.BadRequestException;
import com.kickdrum.hospitalStaffingApp.model.ShiftType;
import com.kickdrum.hospitalStaffingApp.repository.ShiftTypeJdbcRepository;

@Service
public class ShiftTypeService {

    private final ShiftTypeJdbcRepository repo;

    public ShiftTypeService(ShiftTypeJdbcRepository repo) {
        this.repo = repo;
    }

    public void create(CreateShiftTypeRequest req) {
        if (req.getName() == null || req.getTenantId() == null) {
            throw new BadRequestException("Invalid shift type");
        }

        ShiftType st = new ShiftType();
        st.setId(UUID.randomUUID());
        st.setName(req.getName());
        st.setDescription(req.getDescription());
        st.setActiveStatus(req.isActiveStatus());
        st.setTenantId(req.getTenantId());

        repo.save(st);
    }
}
