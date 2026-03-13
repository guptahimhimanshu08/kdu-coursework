package com.kickdrum.jpaproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kickdrum.jpaproject.dto.CreateShiftTypeRequest;
import com.kickdrum.jpaproject.service.ShiftTypeService;

@RestController
@RequestMapping("/shift-types")
public class ShiftTypeController {

    private final ShiftTypeService service;

    public ShiftTypeController(ShiftTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateShiftTypeRequest req) {
        service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
