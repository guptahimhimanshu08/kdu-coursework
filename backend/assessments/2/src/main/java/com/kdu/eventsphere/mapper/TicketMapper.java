package com.kdu.eventsphere.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.kdu.eventsphere.domain.ticket.Ticket;
import com.kdu.eventsphere.dto.request.BuyTicketRequest;
import com.kdu.eventsphere.dto.response.BookedTicketResponse;
import com.kdu.eventsphere.dto.response.ReservedTicketResponse;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "status", constant = "AVAILABLE")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eventDate", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "transactionId", ignore = true)
    Ticket toEntity(BuyTicketRequest request);

    ReservedTicketResponse toResponse(Ticket ticket);
    BookedTicketResponse toBookedResponse(Ticket ticket);
}
