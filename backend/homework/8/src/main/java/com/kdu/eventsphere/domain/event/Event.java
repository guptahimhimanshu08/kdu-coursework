package com.kdu.eventsphere.domain.event;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kdu.eventsphere.domain.enums.EventStatus;
import com.kdu.eventsphere.domain.ticket.Ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
/**handling soft deletes and recovering deleted records */
@SQLDelete(sql = "UPDATE events SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
@EntityListeners(AuditingEntityListener.class)
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String eventName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @JoinColumn(name = "ticket_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    @Column(nullable = false)
    private Integer ticketCount;

    @Column(nullable = false)
    private String eventDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
 
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    
    public Event(Ticket ticket, Integer ticketCount, String eventDate) {
        this.ticket = ticket;
        this.ticketCount = ticketCount;

        this.eventDate = eventDate;
    }

    public static Event create(String eventName) {
        Event event = new Event();
        event.eventName = eventName;
        event.status = EventStatus.ONGOING;
        return event;
    }

}
