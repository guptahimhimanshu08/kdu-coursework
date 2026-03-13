package com.kickdrum.jpaproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kickdrum.jpaproject.dto.TenantOnboardingRequest;
import com.kickdrum.jpaproject.model.Shift;
import com.kickdrum.jpaproject.model.ShiftType;
import com.kickdrum.jpaproject.model.User;
import com.kickdrum.jpaproject.repository.ShiftJpaRepository;
import com.kickdrum.jpaproject.repository.ShiftTypeJpaRepository;
import com.kickdrum.jpaproject.repository.UserJpaRepository;


@Service
public class TenantOnboardingService {

    private final ShiftTypeJpaRepository shiftTypeRepo;
    private final ShiftJpaRepository shiftRepo;
    private final UserJpaRepository userRepo;

    public TenantOnboardingService(
            ShiftTypeJpaRepository shiftTypeRepo,
            ShiftJpaRepository shiftRepo,
            UserJpaRepository userRepo
    ) {
        this.shiftTypeRepo = shiftTypeRepo;
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void onboardTenant(TenantOnboardingRequest request) {

        request.getShiftTypes().forEach(dto -> {
            ShiftType st = new ShiftType();
            st.setName(dto.getName());
            st.setDescription(dto.getDescription());
            st.setActiveStatus(dto.isActiveStatus());
            st.setTenantId(dto.getTenantId());
            shiftTypeRepo.save(st);
        });

        request.getShifts().forEach(dto -> {
            Shift shift = new Shift();
            shift.setStartDate(dto.getStartDate());
            shift.setEndDate(dto.getEndDate());
            shift.setStartTime(dto.getStartTime());
            shift.setEndTime(dto.getEndTime());
            shift.setTenantId(dto.getTenantId());

            ShiftType st = shiftTypeRepo.findById(dto.getShiftTypeId())
                .orElseThrow();

            shift.setShiftType(st);
            shiftRepo.save(shift);
        });

        request.getUsers().forEach(dto -> {
            if (dto.getUsername() == null) {
                throw new RuntimeException("Forced failure");
            }

            User user = new User();
            user.setUsername(dto.getUsername());
            user.setTimezone(dto.getTimezone());
            user.setTenantId(dto.getTenantId());

            userRepo.save(user);
        });
    }

}
