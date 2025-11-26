package com.aptechph.ticket_booking_system.customers.mapper;

import com.aptechph.ticket_booking_system.customers.dto.CustomerRequestDto;
import com.aptechph.ticket_booking_system.customers.dto.CustomerResponseDto;
import com.aptechph.ticket_booking_system.customers.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDto toResponseDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CustomerRequestDto requestDto, @MappingTarget Customer customer);
}
