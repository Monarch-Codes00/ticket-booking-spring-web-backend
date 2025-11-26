package com.aptechph.ticket_booking_system.customers.service.impl;

import com.aptechph.ticket_booking_system.customers.dto.CustomerRequestDto;
import com.aptechph.ticket_booking_system.customers.dto.CustomerResponseDto;
import com.aptechph.ticket_booking_system.customers.mapper.CustomerMapper;
import com.aptechph.ticket_booking_system.customers.model.Customer;
import com.aptechph.ticket_booking_system.customers.repository.CustomerRepository;
import com.aptechph.ticket_booking_system.customers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        Customer entity = mapper.toEntity(requestDto);
        Customer saved = repository.save(entity);
        return mapper.toResponseDto(saved);
    }

    @Override
    public CustomerResponseDto getCustomerById(Long id) {
        return repository.findById(id).map(mapper::toResponseDto).orElse(null);
    }

    @Override
    public List<CustomerResponseDto> getAllCustomers() {
        return repository.findAll().stream().map(mapper::toResponseDto).collect(Collectors.toList());
    }
}
