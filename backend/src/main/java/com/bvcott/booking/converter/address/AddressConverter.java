package com.bvcott.booking.converter.address;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.address.AddressDTO;
import com.bvcott.booking.model.address.Address;

@Component
public class AddressConverter {
    public AddressDTO toDto(Address address) {
        return AddressDTO.builder()
            .streetNumber1(address.getStreetNumber1())
            .streetNumber2(address.getStreetNumber2())
            .postCode(address.getPostCode())
            .city(address.getCity())
            .country(address.getCountry())
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
