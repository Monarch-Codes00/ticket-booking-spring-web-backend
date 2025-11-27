package com.aptechph.ticket_booking_system.bookings.repository;

import com.aptechph.ticket_booking_system.bookings.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByCustomerId(Long customerId);

    List<Booking> findByEventId(Long eventId);

    @Query("SELECT b FROM Booking b WHERE b.customer.id = :customerId AND b.bookingStatus = 'CONFIRMED'")
    List<Booking> findActiveBookingsByCustomerId(@Param("customerId") Long customerId);

    boolean existsByBookingReference(String bookingReference);
}
