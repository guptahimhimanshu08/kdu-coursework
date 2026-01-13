package com.kickdrum.hospitalStaffingApp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kickdrum.hospitalStaffingApp.dto.TenantOnboardingRequest;
import com.kickdrum.hospitalStaffingApp.service.TenantOnboardingService;

@RestController
@RequestMapping("/tenants")
public class TenantOnboardingController {

    private final TenantOnboardingService service;

    public TenantOnboardingController(TenantOnboardingService service) {
        this.service = service;
    }

    @PostMapping("/onboard")
    public void onboard(@RequestBody TenantOnboardingRequest request) {
        service.onboardTenant(request);
    }
}
