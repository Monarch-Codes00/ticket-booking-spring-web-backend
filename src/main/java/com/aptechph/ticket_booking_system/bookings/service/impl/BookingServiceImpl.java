package com.aptechph.ticket_booking_system.bookings.service.impl;

import com.aptechph.ticket_booking_system.bookings.dto.BookingRequestDto;
import com.aptechph.ticket_booking_system.bookings.dto.BookingResponseDto;
import com.aptechph.ticket_booking_system.bookings.model.Booking;
import com.aptechph.ticket_booking_system.bookings.repository.BookingRepository;
import com.aptechph.ticket_booking_system.bookings.service.BookingService;
import com.aptechph.ticket_booking_system.customers.model.Customer;
import com.aptechph.ticket_booking_system.customers.repository.CustomerRepository;
import com.aptechph.ticket_booking_system.events.model.Event;
import com.aptechph.ticket_booking_system.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return mapToResponseDto(booking);
    }

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto) {
        // Validate event exists and has available seats
        Event event = eventRepository.findById(requestDto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + requestDto.getEventId()));

        // Validate customer exists
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + requestDto.getCustomerId()));

        // Check seat availability
        if (event.getAvailableSeats() < requestDto.getNumberOfTickets()) {
            throw new RuntimeException("Not enough seats available. Available: " + event.getAvailableSeats());
        }

        // Calculate total amount
        BigDecimal totalAmount = event.getPrice().multiply(BigDecimal.valueOf(requestDto.getNumberOfTickets()));

        // Create booking
        Booking booking = new Booking();
        booking.setEvent(event);
        booking.setCustomer(customer);
        booking.setNumberOfTickets(requestDto.getNumberOfTickets());
        booking.setTotalAmount(totalAmount);
        booking.setPaymentMethod(requestDto.getPaymentMethod());
        booking.setBookingStatus("CONFIRMED");
        booking.setBookingReference(generateBookingReference());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        // Update available seats
        event.setAvailableSeats(event.getAvailableSeats() - requestDto.getNumberOfTickets());
        eventRepository.save(event);

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponseDto(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponseDto cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        if ("CANCELLED".equals(booking.getBookingStatus())) {
            throw new RuntimeException("Booking is already cancelled");
        }

        // Update booking status
        booking.setBookingStatus("CANCELLED");
        booking.setUpdatedAt(LocalDateTime.now());

        // Return seats to event
        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + booking.getNumberOfTickets());
        eventRepository.save(event);

        Booking savedBooking = bookingRepository.save(booking);
        return mapToResponseDto(savedBooking);
    }

    @Override
    public List<BookingResponseDto> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getBookingsByEventId(Long eventId) {
        return bookingRepository.findByEventId(eventId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponseDto> getActiveBookingsByCustomerId(Long customerId) {
        return bookingRepository.findActiveBookingsByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private BookingResponseDto mapToResponseDto(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setEventId(booking.getEvent().getId());
        dto.setCustomerId(booking.getCustomer().getId());
        dto.setNumberOfTickets(booking.getNumberOfTickets());
        dto.setTotalAmount(booking.getTotalAmount());
        dto.setPaymentMethod(booking.getPaymentMethod());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setBookingReference(booking.getBookingReference());
        dto.setCreatedAt(booking.getCreatedAt().toString());
        return dto;
    }

    private String generateBookingReference() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
