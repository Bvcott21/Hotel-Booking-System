package com.bvcott.booking.mapping.service.address;

import org.springframework.stereotype.Service;

import com.bvcott.booking.dto.address.AddressDTO;
import com.bvcott.booking.model.address.Address;

@Service
public class AddressMappingService {
    public AddressDTO toDto(Address entity) {
        return AddressDTO.builder()
            .streetNumber1(entity.getStreetNumber1())
            .streetNumber2(entity.getStreetNumber2())
            .postCode(entity.getPostCode())
            .city(entity.getCity())
            .country(entity.getCountry())
            .build();

    }

    public Address toEntity(AddressDTO dto) {
    	Address entity = new Address();
        entity.setStreetNumber1(dto.getStreetNumber1());
        entity.setStreetNumber2(dto.getStreetNumber2());
        entity.setPostCode(dto.getPostCode());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        return entity;
    }
}
