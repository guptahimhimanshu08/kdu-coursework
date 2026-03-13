package com.kickdrum.hospitalStaffingApp.service;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kickdrum.hospitalStaffingApp.dto.CreateUserRequest;
import com.kickdrum.hospitalStaffingApp.dto.UpdateUserRequest;
import com.kickdrum.hospitalStaffingApp.exception.BadRequestException;
import com.kickdrum.hospitalStaffingApp.exception.ResourceNotFoundException;
import com.kickdrum.hospitalStaffingApp.model.User;
import com.kickdrum.hospitalStaffingApp.repository.UserJdbcRepository;
import com.kickdrum.hospitalStaffingApp.util.PaginationUtil;


@Service
public class UserService {

    private final UserJdbcRepository repository;

    public UserService(UserJdbcRepository repository) {
        this.repository = repository;
    }

    public void createUser(CreateUserRequest req) {
        if (req.getUsername() == null || req.getTimezone() == null) {
            throw new BadRequestException("Invalid user data");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(req.getUsername());
        user.setLoggedIn(false);
        user.setTimezone(req.getTimezone());
        user.setTenantId(req.getTenantId());

        repository.save(user);
    }

    public List<User> getUsers(UUID tenantId, int page, int size, String sort) {
        PaginationUtil.validate(page, size);

        int offset = page * size;
        String direction = sort.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        return repository.findByTenantId(tenantId, size, offset, direction);
    }

    public void updateUser(UUID id, UpdateUserRequest req) {
        int updated = repository.updateUser(id, req.getUsername(), req.getTimezone());

        if (updated == 0) {
            throw new ResourceNotFoundException("User not found");
        }
    }
}
