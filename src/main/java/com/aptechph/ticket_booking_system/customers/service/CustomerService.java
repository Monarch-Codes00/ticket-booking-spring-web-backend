package com.aptechph.ticket_booking_system.customers.service;

import com.aptechph.ticket_booking_system.customers.dto.CustomerRequestDto;
import com.aptechph.ticket_booking_system.customers.dto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);
    CustomerResponseDto getCustomerById(Long id);
    List<CustomerResponseDto> getAllCustomers();
}
