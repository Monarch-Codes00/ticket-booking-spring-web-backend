package com.aptechph.ticket_booking_system.bookings.service;

import com.aptechph.ticket_booking_system.bookings.dto.BookingRequestDto;
import com.aptechph.ticket_booking_system.bookings.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    List<BookingResponseDto> getAllBookings();
    BookingResponseDto getBookingById(Long id);
    BookingResponseDto createBooking(BookingRequestDto requestDto);
    BookingResponseDto cancelBooking(Long id);

    List<BookingResponseDto> getBookingsByCustomerId(Long customerId);
    List<BookingResponseDto> getBookingsByEventId(Long eventId);
    List<BookingResponseDto> getActiveBookingsByCustomerId(Long customerId);
}
