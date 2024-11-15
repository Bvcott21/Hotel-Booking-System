package com.bvcott.booking.service.hotel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.address.AddressConverter;
import com.bvcott.booking.converter.hotel.HotelConverter;
import com.bvcott.booking.dto.hotel.HotelCreateDTO;
import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.hotel.HotelRoomType;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.hotel.HotelRepository;
import com.bvcott.booking.repository.user.HotelOwnerRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class HotelService {
    private static final Logger log = LoggerFactory.getLogger(HotelService.class);
    private final HotelRepository hotelRepo;
    private final HotelConverter hotelConverter;
    private final HotelOwnerRepository ownerRepo;
    private final AddressConverter addressConverter;

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

    public HotelDTO createHotel(UUID hotelOwnerId, HotelCreateDTO newHotel) {
        log.info("createHotel triggered with values: onwerId: {} - DTO: {}", hotelOwnerId, newHotel);
        HotelOwner owner = ownerRepo
            .findById(hotelOwnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel Owner not found with the provided ID."));
        
        log.debug("Hotel owner found, creating hotel...");
        Hotel hotel = new Hotel();
        
        hotel.setName(newHotel.getName());
        hotel.setDescription(newHotel.getDescription());
        hotel.setAddress(addressConverter.toEntity(newHotel.getAddress()));
        hotel.setFacilities(newHotel
            .getFacilities()
            .stream()
            .map(hotelConverter::mapStringToFacility)
            .collect(Collectors.toList()));
        
        log.debug("Creating {} single rooms with price {}", newHotel.getNumberOfSingleRooms(), newHotel.getPriceOfSingleRooms());
        for(int i = 0; i < newHotel.getNumberOfSingleRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.SINGLE);
            room.setPrice(newHotel.getPriceOfSingleRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} double rooms with price {}", newHotel.getNumberOfDoubleRooms(), newHotel.getPriceOfDoubleRooms());
        for(int i = 0; i < newHotel.getNumberOfDoubleRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.DOUBLE);
            room.setPrice(newHotel.getPriceOfDoubleRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} suite rooms with price {}", newHotel.getNumberOfSuiteRooms(), newHotel.getPriceOfSuiteRooms());
        for(int i = 0; i < newHotel.getNumberOfSuiteRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.SUITE);
            room.setPrice(newHotel.getPriceOfSuiteRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} family rooms with price {}", newHotel.getNumberOfFamilyRooms(), newHotel.getPriceOfFamilyRooms());
        for(int i = 0; i < newHotel.getNumberOfFamilyRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.FAMILY);
            room.setPrice(newHotel.getPriceOfFamilyRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} deluxe rooms with price {}", newHotel.getNumberOfDeluxeRooms(), newHotel.getPriceOfDeluxeRooms());
        for(int i = 0; i < newHotel.getNumberOfDeluxeRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.DELUXE);
            room.setPrice(newHotel.getPriceOfDeluxeRooms());
            hotel.addHotelRoom(room);
        }

        owner.addHotel(hotel);
        log.debug("Hotel created successfully, persisting...");

        owner = ownerRepo.save(owner);
        log.debug("Hotel persisted succesfully");
        
        log.info("Hotel created successfully.");
        return hotelConverter.toDto(owner.getHotels().get(owner.getHotels().size() - 1));
    }
}
