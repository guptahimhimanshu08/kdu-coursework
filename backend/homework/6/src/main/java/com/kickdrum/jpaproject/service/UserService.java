package com.kickdrum.jpaproject.service;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kickdrum.jpaproject.dto.CreateUserRequest;
import com.kickdrum.jpaproject.dto.UpdateUserRequest;
import com.kickdrum.jpaproject.exception.BadRequestException;
import com.kickdrum.jpaproject.exception.ResourceNotFoundException;
import com.kickdrum.jpaproject.model.User;
import com.kickdrum.jpaproject.repository.UserJpaRepository;
import com.kickdrum.jpaproject.util.PaginationUtil;


@Service
public class UserService {

    private final UserJpaRepository repository;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 50;
    private static final int MAX_SIZE = 50;

    public UserService(UserJpaRepository repository) {
        this.repository = repository;
    }

    public void createUser(CreateUserRequest req) {
        if (req.getUsername() == null || req.getTimezone() == null) {
            throw new BadRequestException("Invalid user data");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setLoggedIn(false);
        user.setTimezone(req.getTimezone());
        user.setTenantId(req.getTenantId());

        repository.save(user);
    }

    public Page<User> getUsers(UUID tenantId, Integer page,
            Integer size) {
        PaginationUtil.validate(page, size);
        
        int resolvedPage = (page == null) ? DEFAULT_PAGE : page;
        int resolvedSize = (size == null) ? DEFAULT_SIZE : size;
        if (resolvedSize < 1 || resolvedSize > MAX_SIZE) {
            throw new BadRequestException(
                "Size must be between 1 and 50"
            );
        }

        if (resolvedPage < 0) {
            throw new BadRequestException(
                "Page index must be 0 or greater"
            );
        }

        Pageable pageable = PageRequest.of(
            resolvedPage,
            resolvedSize,
            Sort.by("username").ascending()
        );
        

        return repository.findByTenantId(tenantId, pageable);
    }

    // public void updateUser(UUID id, UpdateUserRequest req) {
    //     int updated = repository.updateUser(id, req.getUsername(), req.getTimezone());

    //     if (updated == 0) {
    //         throw new ResourceNotFoundException("User not found");
    //     }
    // }
}
