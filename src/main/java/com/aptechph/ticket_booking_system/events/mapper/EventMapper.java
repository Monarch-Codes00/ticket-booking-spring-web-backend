package com.aptechph.ticket_booking_system.events.mapper;

import com.aptechph.ticket_booking_system.events.dto.EventRequestDto;
import com.aptechph.ticket_booking_system.events.dto.EventResponseDto;
import com.aptechph.ticket_booking_system.events.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "date", expression = "java(event.getEventDate().toLocalDate().format(DateTimeFormatter.ofPattern(\"MMM dd, yyyy\")))")
    @Mapping(target = "time", expression = "java(event.getEventDate().toLocalTime().format(DateTimeFormatter.ofPattern(\"h:mm a\")))")
    @Mapping(target = "image", source = "imageUrl")
    EventResponseDto toResponseDto(Event event);

    @Mapping(target = "eventDate", source = "eventDate")
    @Mapping(target = "imageUrl", source = "imageUrl")
    Event toEntity(EventRequestDto requestDto);
}
