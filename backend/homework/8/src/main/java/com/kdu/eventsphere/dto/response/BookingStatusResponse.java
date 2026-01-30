package com.kdu.eventsphere.dto.response;

public record BookingStatusResponse(
    String bookingId,
    String status,
    String message
) {
    public static BookingStatusResponse inProgress(String bookingId) {
        return new BookingStatusResponse(
            bookingId,
            "IN_PROGRESS",
            "Booking in Progress"
        );
    }

    public static BookingStatusResponse completed(String bookingId) {
        return new BookingStatusResponse(
            bookingId,
            "COMPLETED",
            "Booking completed successfully"
        );
    }

    public static BookingStatusResponse failed(String bookingId, String reason) {
        return new BookingStatusResponse(
            bookingId,
            "FAILED",
            "Booking failed: " + reason
        );
    }
}
