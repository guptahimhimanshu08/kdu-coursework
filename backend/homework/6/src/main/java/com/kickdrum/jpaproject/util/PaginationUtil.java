package com.kickdrum.jpaproject.util;

import com.kickdrum.jpaproject.exception.BadRequestException;

public class PaginationUtil {

    public static void validate(int page, int size) {
        if (page < 0 || size < 1 || size > 50) {
            throw new BadRequestException("Invalid pagination params");
        }
    }
}
