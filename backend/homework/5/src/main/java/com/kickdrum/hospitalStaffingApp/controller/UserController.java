package com.kickdrum.hospitalStaffingApp.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kickdrum.hospitalStaffingApp.dto.CreateUserRequest;
import com.kickdrum.hospitalStaffingApp.dto.UpdateUserRequest;
import com.kickdrum.hospitalStaffingApp.model.User;
import com.kickdrum.hospitalStaffingApp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateUserRequest req) {
        service.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam UUID tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        return service.getUsers(tenantId, page, size, sort);
    }

    @PutMapping("/{id}")
    public void update(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequest req
    ) {
        service.updateUser(id, req);
    }
}
