package com.aptechph.ticket_booking_system.customers.repository;

import com.aptechph.ticket_booking_system.customers.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
