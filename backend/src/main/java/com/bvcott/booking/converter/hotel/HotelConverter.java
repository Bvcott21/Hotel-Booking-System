package com.bvcott.booking.converter.hotel;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bvcott.booking.converter.address.AddressConverter;
import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.model.hotel.Facility;
import com.bvcott.booking.model.hotel.Hotel;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor
public class HotelConverter {
    private final AddressConverter addressConverter;
    private final HotelRoomConverter roomConverter;

    public HotelDTO toDto(Hotel entity) {
        return HotelDTO.builder()
            .hotelId(entity.getHotelId())
            .name(entity.getName())
            .description(entity.getDescription())
            .rating(entity.getRating())
            .address(addressConverter.toDto(entity.getAddress()))
            .facilities(entity
                .getFacilities()
                .stream()
                .map(Facility::getDisplayName)
                .collect(Collectors.toList()))
            .hotelRooms(entity
                .getRooms()
                .stream()
                .map(roomConverter::toDto)
                .collect(Collectors.toList()))
            .build();
    }

    public Hotel toEntity(HotelDTO dto) {
        Hotel entity = new Hotel();

        entity.setHotelId(dto.getHotelId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setRating(dto.getRating());
        entity.setAddress(addressConverter.toEntity(dto.getAddress()));
        entity.setFacilities(dto
            .getFacilities()
            .stream()
            .map(this::mapStringToFacility)
            .collect(Collectors.toList()));
        entity.setRooms(dto
            .getHotelRooms()
            .stream()
            .map(roomConverter::toEntity)
            .collect(Collectors.toList()));
        return entity;
    }

    private Facility mapStringToFacility(String displayName) {
        for (Facility facility :Facility.values()) {
            if (facility.getDisplayName().equalsIgnoreCase(displayName)) {
                return facility;
            }
        }

        throw new IllegalArgumentException("Invalid facility name: " + displayName);
    }
}
