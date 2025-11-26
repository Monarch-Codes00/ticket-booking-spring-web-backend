package com.aptechph.ticket_booking_system.bookings.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BookingResponseDto {
    private Long id;
    private Long eventId;
    private Long customerId;
    private Integer numberOfTickets;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String bookingStatus;
    private String bookingReference;
    private String createdAt;
}
