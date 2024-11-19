package com.bvcott.booking.converter.address;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.address.AddressDTO;
import com.bvcott.booking.mapping.service.address.AddressMappingService;
import com.bvcott.booking.model.address.Address;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor
public class AddressConverter {
    private final AddressMappingService mappingService;
    
    public AddressDTO toDto(Address entity) {
        return mappingService.toDto(entity);
    }

    public Address toEntity(AddressDTO dto) {
        return mappingService.toEntity(dto);
    }
}
