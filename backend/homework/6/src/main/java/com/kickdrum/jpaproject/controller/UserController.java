package com.kickdrum.jpaproject.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kickdrum.jpaproject.dto.CreateUserRequest;
import com.kickdrum.jpaproject.dto.UpdateUserRequest;
import com.kickdrum.jpaproject.model.User;
import com.kickdrum.jpaproject.service.UserService;

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
    public Page<User> getUsers(
            @RequestParam UUID tenantId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return service.getUsers(tenantId, page, size);
    }

    // @PutMapping("/{id}")
    // public void update(
    //         @PathVariable UUID id,
    //         @RequestBody UpdateUserRequest req
    // ) {
    //     service.updateUser(id, req);
    // }
}
