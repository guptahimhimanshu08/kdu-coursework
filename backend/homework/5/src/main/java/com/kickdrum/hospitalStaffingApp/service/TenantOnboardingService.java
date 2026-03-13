package com.kickdrum.hospitalStaffingApp.service;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kickdrum.hospitalStaffingApp.dto.TenantOnboardingRequest;
import com.kickdrum.hospitalStaffingApp.repository.ShiftJdbcRepository;
import com.kickdrum.hospitalStaffingApp.repository.ShiftTypeJdbcRepository;
import com.kickdrum.hospitalStaffingApp.repository.UserJdbcRepository;

@Service
public class TenantOnboardingService {

    private final ShiftTypeJdbcRepository shiftTypeRepo;
    private final ShiftJdbcRepository shiftRepo;
    private final UserJdbcRepository userRepo;

    public TenantOnboardingService(
            ShiftTypeJdbcRepository shiftTypeRepo,
            ShiftJdbcRepository shiftRepo,
            UserJdbcRepository userRepo
    ) {
        this.shiftTypeRepo = shiftTypeRepo;
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void onboardTenant(TenantOnboardingRequest request) {

        request.getShiftTypes().forEach(shiftTypeRepo::save);
        request.getShifts().forEach(shiftRepo::save);

        // FAILURE POINT
        request.getUsers().forEach(user -> {
            if (user.getUsername() == null) {
                throw new RuntimeException("Forced failure");
            }
            userRepo.save(user);
        });
        // throw new RuntimeException("Forced failure");
    }
}
