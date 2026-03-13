package com.kdu.eventsphere.domain.ticket;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.kdu.eventsphere.domain.enums.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tickets")
@EntityListeners(AuditingEntityListener.class)
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String eventDate;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Double price;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    @Column(nullable = true)
    private long version;

    @Column(nullable = true)
    private UUID transactionId;

    public Ticket(String eventName, Double price, String eventDate, com.kdu.eventsphere.domain.enums.Status status, UUID transactionId) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.status = status;
        this.price = price;
        this.transactionId = transactionId;
    }

    public static Ticket create(String eventName, Double price, String eventDate) {
        Ticket ticket = new Ticket();
        ticket.eventName = eventName;
        ticket.eventDate = eventDate;
        ticket.price = price;
        ticket.status = Status.AVAILABLE;
        return ticket;
    }

    
    public void markAvailable() {
        if (this.status != Status.BOOKED && this.status != Status.RESERVED) {
            throw new IllegalStateException(
                "INVALID_STATE_TRANSITION: " + status + " → AVAILABLE"
            );
        }
        this.status = Status.AVAILABLE;
    }

    public void markBooked() {

        if ( this.status != Status.RESERVED || this.status == Status.BOOKED ) {
            throw new IllegalStateException("TICKET_NOT_AVAILABLE_FOR_BOOKING");
        }
        this.status = Status.BOOKED;
    }

    public void markReserved() {

        if (this.status != Status.AVAILABLE) {
            throw new IllegalStateException("TICKET_NOT_AVAILABLE");
        }
        this.status = Status.RESERVED;
    }

    
}
