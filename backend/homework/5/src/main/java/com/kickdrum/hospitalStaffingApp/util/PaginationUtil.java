package com.kickdrum.hospitalStaffingApp.util;

import com.kickdrum.hospitalStaffingApp.exception.BadRequestException;

public class PaginationUtil {

    public static void validate(int page, int size) {
        if (page < 0 || size < 1 || size > 50) {
            throw new BadRequestException("Invalid pagination params");
        }
    }
}
