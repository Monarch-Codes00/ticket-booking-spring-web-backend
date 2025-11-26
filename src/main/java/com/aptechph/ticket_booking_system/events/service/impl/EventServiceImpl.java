package com.aptechph.ticket_booking_system.events.service.impl;

import com.aptechph.ticket_booking_system.events.dto.EventRequestDto;
import com.aptechph.ticket_booking_system.events.dto.EventResponseDto;
import com.aptechph.ticket_booking_system.events.mapper.EventMapper;
import com.aptechph.ticket_booking_system.events.model.Event;
import com.aptechph.ticket_booking_system.events.repository.EventRepository;
import com.aptechph.ticket_booking_system.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventResponseDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return eventMapper.toResponseDto(event);
    }

    @Override
    @Transactional
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        Event event = eventMapper.toEntity(requestDto);
        Event savedEvent = eventRepository.save(event);
        return eventMapper.toResponseDto(savedEvent);
    }

    @Override
    @Transactional
    public EventResponseDto updateEvent(Long id, EventRequestDto requestDto) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        // Update fields
        existingEvent.setTitle(requestDto.getTitle());
        existingEvent.setDescription(requestDto.getDescription());
        existingEvent.setCategory(requestDto.getCategory());
        existingEvent.setVenue(requestDto.getVenue());
        existingEvent.setLocation(requestDto.getLocation());
        existingEvent.setEventDate(requestDto.getEventDate());
        existingEvent.setPrice(requestDto.getPrice());
        existingEvent.setAvailableSeats(requestDto.getAvailableSeats());
        existingEvent.setImageUrl(requestDto.getImageUrl());
        Event savedEvent = eventRepository.save(existingEvent);
        return eventMapper.toResponseDto(savedEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found");
        }
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventResponseDto> getEventsByCategory(String category) {
        return eventRepository.findByCategory(category).stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDto> getEventsByLocation(String location) {
        return eventRepository.findByLocation(location).stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDto> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.findByEventDateBetween(startDate, endDate).stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDto> searchEvents(String keyword) {
        return eventRepository.searchByKeyword(keyword).stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDto> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(LocalDateTime.now()).stream()
                .map(eventMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasAvailableSeats(Long eventId, int requestedSeats) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return event.getAvailableSeats() >= requestedSeats;
    }

    @Override
    @Transactional
    public void updateAvailableSeats(Long eventId, int seatsToReduce) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (event.getAvailableSeats() < seatsToReduce) {
            throw new RuntimeException("Not enough available seats");
        }
        event.setAvailableSeats(event.getAvailableSeats() - seatsToReduce);
        eventRepository.save(event);
    }
}
