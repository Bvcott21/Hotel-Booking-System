package com.bvcott.booking.service.hotel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.hotel.HotelConverter;
import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.repository.hotel.HotelRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepo;
    private final HotelConverter hotelConverter;

    public List<HotelDTO> findAll() {
        return hotelRepo
            .findAll()
            .stream()
            .map(hotelConverter::toDto)
            .collect(Collectors.toList());
    }

    public HotelDTO findById(UUID id) {
        return hotelConverter.toDto(hotelRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Could not find Hotel with the provided ID")));
    }
}
