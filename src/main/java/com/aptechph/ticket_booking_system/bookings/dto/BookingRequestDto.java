package com.aptechph.ticket_booking_system.bookings.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookingRequestDto {
    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Number of tickets is required")
    @Min(value = 1, message = "At least 1 ticket must be booked")
    @Max(value = 10, message = "Maximum 10 tickets can be booked at once")
    private Integer numberOfTickets;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
