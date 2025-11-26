package com.aptechph.ticket_booking_system.events.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class EventResponseDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String venue;
    private String location;
    // date and time are strings to match frontend mock shape
    private String date;
    private String time;
    private BigDecimal price;
    private Integer availableSeats;
    private String image;
}
