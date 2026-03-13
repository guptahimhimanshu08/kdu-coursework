package com.kickdrum.jpaproject.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kickdrum.jpaproject.model.Shift;

public interface ShiftJpaRepository extends JpaRepository<Shift, UUID> {
    @Query("""
        SELECT s FROM Shift s
        WHERE s.startDate = :startDate
        AND s.endDate <= :endDate
        ORDER BY s.shiftName ASC
    """)
    List<Shift> findTopNewYearShifts(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );
}

