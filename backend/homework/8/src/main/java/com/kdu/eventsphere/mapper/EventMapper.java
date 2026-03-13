package com.kdu.eventsphere.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.kdu.eventsphere.domain.event.Event;
import com.kdu.eventsphere.dto.request.CreateEventRequest;
import com.kdu.eventsphere.dto.response.EventResponse;


@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventName", source = "eventName")
    @Mapping(target = "status", constant = "ONGOING")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "ticket", ignore = true)
    @Mapping(target = "ticketCount", source = "ticketCount")
    @Mapping(target = "eventDate", source = "eventDate")
    Event toEntity(CreateEventRequest request);
    CreateEventRequest toRequest(Event event);
    EventResponse toResponse(Event event);
}
