package com.aptechph.ticket_booking_system.events.service;

import com.aptechph.ticket_booking_system.events.dto.EventRequestDto;
import com.aptechph.ticket_booking_system.events.dto.EventResponseDto;
import com.aptechph.ticket_booking_system.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<EventResponseDto> getAllEvents();
    EventResponseDto getEventById(Long id);
    EventResponseDto createEvent(EventRequestDto requestDto);
    EventResponseDto updateEvent(Long id, EventRequestDto requestDto);
    void deleteEvent(Long id);

    List<EventResponseDto> getEventsByCategory(String category);
    List<EventResponseDto> getEventsByLocation(String location);
    List<EventResponseDto> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<EventResponseDto> searchEvents(String keyword);
    List<EventResponseDto> getUpcomingEvents();

    boolean hasAvailableSeats(Long eventId, int requestedSeats);
    void updateAvailableSeats(Long eventId, int seatsToReduce);
}
