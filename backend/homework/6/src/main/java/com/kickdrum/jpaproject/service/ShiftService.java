package com.kickdrum.jpaproject.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kickdrum.jpaproject.model.Shift;
import com.kickdrum.jpaproject.repository.ShiftJpaRepository;

@Service
public class ShiftService {

    private final ShiftJpaRepository shiftJpaRepository;

    public ShiftService(ShiftJpaRepository shiftJpaRepository) {
        this.shiftJpaRepository = shiftJpaRepository;
    }

    public List<Shift> getNewYearTopShifts() {

        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end   = LocalDate.of(2023, 1, 25);

        Pageable pageable = PageRequest.of(
            0,
            3,
            Sort.by("shiftName").ascending()
        );

        return shiftJpaRepository.findTopNewYearShifts(
            start,
            end,
            pageable
        );
    }
}
