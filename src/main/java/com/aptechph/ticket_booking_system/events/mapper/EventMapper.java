package com.aptechph.ticket_booking_system.events.mapper;

import com.aptechph.ticket_booking_system.events.dto.EventRequestDto;
import com.aptechph.ticket_booking_system.events.dto.EventResponseDto;
import com.aptechph.ticket_booking_system.events.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "date", ignore = true)
    @Mapping(target = "time", ignore = true)
    @Mapping(target = "image", source = "imageUrl")
    EventResponseDto toResponseDto(Event event);

    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Event toEntity(EventRequestDto requestDto);
}
