package com.bvcott.booking.service.hotel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.hotel.HotelConverter;
import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.hotel.HotelRepository;
import com.bvcott.booking.repository.user.HotelOwnerRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepo;
    private final HotelConverter hotelConverter;
    private final HotelOwnerRepository ownerRepo;

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

    public HotelDTO createHotel(UUID hotelOwnerId, HotelDTO newHotel) {
        HotelOwner owner = ownerRepo
            .findById(hotelOwnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel Owner not found with the provided ID."));

        owner.getHotels().add(hotelConverter.toEntity(newHotel));

        HotelOwner updatedOwner = ownerRepo.save(owner);
        Hotel createdHotel = updatedOwner.getHotels().get(updatedOwner.getHotels().size() - 1);
        
        return hotelConverter.toDto(createdHotel);
    }
}
