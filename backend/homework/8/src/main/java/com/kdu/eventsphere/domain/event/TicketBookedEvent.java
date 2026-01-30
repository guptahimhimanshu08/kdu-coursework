package com.kdu.eventsphere.domain.event;

public class TicketBookedEvent {

    private String eventId;
    private String bookingId;
    private String seatNo;
    private String phoneNumber;
    
    public TicketBookedEvent(String eventId, String bookingId, String seatNo, String phoneNumber) {
        this.eventId = eventId;
        this.bookingId = bookingId;
        this.seatNo = seatNo;
        this.phoneNumber = phoneNumber;

    }
    // getters & setters

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
