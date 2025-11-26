package com.aptechph.ticket_booking_system.customers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
}
