package com.kickdrum.jpaproject.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kickdrum.jpaproject.model.Shift;
import com.kickdrum.jpaproject.model.User;

public interface UserJpaRepository extends JpaRepository<User, UUID> {
     

    Page<User> findByTenantId(UUID tenantId, Pageable pageable);
}
