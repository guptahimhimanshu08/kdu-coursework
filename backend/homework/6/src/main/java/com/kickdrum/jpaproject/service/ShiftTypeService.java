package com.kickdrum.jpaproject.service;


import org.springframework.stereotype.Service;

import com.kickdrum.jpaproject.dto.CreateShiftTypeRequest;
import com.kickdrum.jpaproject.exception.BadRequestException;
import com.kickdrum.jpaproject.model.ShiftType;
import com.kickdrum.jpaproject.repository.ShiftTypeJpaRepository;

@Service
public class ShiftTypeService {

    private final ShiftTypeJpaRepository repo;

    public ShiftTypeService(ShiftTypeJpaRepository repo) {
        this.repo = repo;
    }

    
    public ShiftType create(CreateShiftTypeRequest req) {
        if (req.getName() == null || req.getTenantId() == null) {
            throw new BadRequestException("Invalid shift type");
        }

        ShiftType shiftType = new ShiftType();
        shiftType.setName(req.getName());
        shiftType.setDescription(req.getDescription());
        shiftType.setActiveStatus(req.isActiveStatus());
        shiftType.setTenantId(req.getTenantId());

        repo.save(shiftType);
        return shiftType;
    }
}
