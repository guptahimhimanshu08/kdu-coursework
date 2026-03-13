package com.kdu.eventsphere.domain.event;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    Optional<Event> findByEventName(String eventName);
    // Optional<Event> findByTicketIdAndReturnedAtIsNull(UUID ticketId);
}
