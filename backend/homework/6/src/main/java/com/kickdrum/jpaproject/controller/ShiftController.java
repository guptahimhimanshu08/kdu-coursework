package com.kickdrum.jpaproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kickdrum.jpaproject.model.Shift;
import com.kickdrum.jpaproject.service.ShiftService;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/new-year-top")
    public List<Shift> getTopNewYearShifts() {
        return shiftService.getNewYearTopShifts();
    }
}