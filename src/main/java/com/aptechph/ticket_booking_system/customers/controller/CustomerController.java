package com.aptechph.ticket_booking_system.customers.controller;

import com.aptechph.ticket_booking_system.customers.dto.CustomerRequestDto;
import com.aptechph.ticket_booking_system.customers.dto.CustomerResponseDto;
import com.aptechph.ticket_booking_system.customers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAll() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getById(@PathVariable Long id) {
        CustomerResponseDto dto = customerService.getCustomerById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CustomerRequestDto dto) {
        CustomerResponseDto created = customerService.createCustomer(dto);
        return ResponseEntity.ok(created);
    }
}
