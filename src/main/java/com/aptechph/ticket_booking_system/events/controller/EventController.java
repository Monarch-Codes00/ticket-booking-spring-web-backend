package com.aptechph.ticket_booking_system.events.controller;

import com.aptechph.ticket_booking_system.CloudinaryUploader;
import com.aptechph.ticket_booking_system.events.dto.EventRequestDto;
import com.aptechph.ticket_booking_system.events.dto.EventResponseDto;
import com.aptechph.ticket_booking_system.events.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final CloudinaryUploader cloudinaryUploader;

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAll(@RequestParam(required = false) String category,
                                                         @RequestParam(required = false) String location,
                                                         @RequestParam(required = false) String q,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(eventService.searchEvents(q));
        }
        if (category != null) {
            return ResponseEntity.ok(eventService.getEventsByCategory(category));
        }
        if (location != null) {
            return ResponseEntity.ok(eventService.getEventsByLocation(location));
        }
        if (start != null && end != null) {
            return ResponseEntity.ok(eventService.getEventsByDateRange(start, end));
        }
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> create(@RequestBody EventRequestDto dto) {
        EventResponseDto created = eventService.createEvent(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/upload-images")
    public ResponseEntity<String> uploadImages() {
        try {
            int eventCount = eventService.getAllEvents().size();
            List<String> imageUrls = cloudinaryUploader.getRandomImageUrls(eventCount);
            eventService.updateEventImages(imageUrls);
            return ResponseEntity.ok("Images uploaded and events updated successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to upload images: " + e.getMessage());
        }
    }
}

