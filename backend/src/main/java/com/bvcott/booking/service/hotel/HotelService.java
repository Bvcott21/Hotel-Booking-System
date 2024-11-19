package com.bvcott.booking.service.hotel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.address.AddressConverter;
import com.bvcott.booking.converter.hotel.HotelConverter;
import com.bvcott.booking.converter.hotel.facility.FacilityConverter;
import com.bvcott.booking.converter.hotel.room.HotelRoomConverter;
import com.bvcott.booking.dto.hotel.HotelCreateUpdateDTO;
import com.bvcott.booking.dto.hotel.HotelDTO;
import com.bvcott.booking.dto.hotel.HotelRoomDTO;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.exception.user.ActionNotAllowedException;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.hotel.HotelRoomType;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.hotel.HotelRepository;
import com.bvcott.booking.repository.hotel.HotelRoomRepository;
import com.bvcott.booking.repository.user.HotelOwnerRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class HotelService {
    private static final Logger log = LoggerFactory.getLogger(HotelService.class);
    private final HotelRepository hotelRepo;
    private final HotelRoomRepository hotelRoomRepo;
    private final HotelConverter hotelConverter;
    private final FacilityConverter facilityConverter;
    private final HotelOwnerRepository ownerRepo;
    private final AddressConverter addressConverter;
    private final HotelRoomConverter roomConverter;

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

    public HotelDTO createHotel(UUID hotelOwnerId, HotelCreateUpdateDTO newHotel) {
        log.info("createHotel triggered with values: onwerId: {} - DTO: {}", hotelOwnerId, newHotel);
        HotelOwner owner = findHotelOwnerById(hotelOwnerId);
        
        log.debug("Hotel owner found, creating hotel...");
        Hotel hotel = new Hotel();
        
        hotel.setName(newHotel.getName());
        hotel.setDescription(newHotel.getDescription());
        hotel.setAddress(addressConverter.toEntity(newHotel.getAddress()));
        hotel.setFacilities(newHotel
            .getFacilities()
            .stream()
            .map(facilityConverter::mapStringToFacility)
            .collect(Collectors.toList()));
        
        log.debug("Creating {} single rooms with price {}...", newHotel.getNumberOfSingleRooms(), newHotel.getPriceOfSingleRooms());
        for(int i = 0; i < newHotel.getNumberOfSingleRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.SINGLE);
            room.setPrice(newHotel.getPriceOfSingleRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} double rooms with price {}...", newHotel.getNumberOfDoubleRooms(), newHotel.getPriceOfDoubleRooms());
        for(int i = 0; i < newHotel.getNumberOfDoubleRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.DOUBLE);
            room.setPrice(newHotel.getPriceOfDoubleRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} suite rooms with price {}...", newHotel.getNumberOfSuiteRooms(), newHotel.getPriceOfSuiteRooms());
        for(int i = 0; i < newHotel.getNumberOfSuiteRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.SUITE);
            room.setPrice(newHotel.getPriceOfSuiteRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} family rooms with price {}...", newHotel.getNumberOfFamilyRooms(), newHotel.getPriceOfFamilyRooms());
        for(int i = 0; i < newHotel.getNumberOfFamilyRooms(); i++) {
            HotelRoom room = new HotelRoom();
            room.setRoomType(HotelRoomType.FAMILY);
            room.setPrice(newHotel.getPriceOfFamilyRooms());
            hotel.addHotelRoom(room);
        }

        log.debug("Creating {} deluxe rooms with price {}...", newHotel.getNumberOfDeluxeRooms(), newHotel.getPriceOfDeluxeRooms());
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

    public void deleteHotel(UUID hotelOwnerId, UUID hotelId) {
        log.info("deleteHotel triggered with values - hotelOwnerId: {}, hotelId: {}", hotelOwnerId, hotelId);

        log.debug("Finding hotel owner...");
        HotelOwner owner = findHotelOwnerById(hotelOwnerId);
        
        log.debug("Hotel owner found, finding hotel...");

        Hotel hotel = hotelRepo
            .findById(hotelId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Hotel with ID " + hotelId + " not found")
            );
        
        log.debug("Hotel found, checking if hotel is associated with owner...");
        
        if(owner.getHotels().contains(hotel)) {
            log.debug("Hotel is associated with owner, deleting...");
            owner.removeHotel(hotel);
            owner = ownerRepo.save(owner);

            hotelRepo.delete(hotel);
            log.info("Hotel deleted successfully");
        } else {
            log.warn("Hotel is not associated with hotel owner, can't delete");
            throw new ActionNotAllowedException("Can't delete an hotel that's not associated with you!");
        }
    }

    public HotelDTO updateHotel(UUID hotelOwnerId, UUID hotelId, HotelCreateUpdateDTO dto) {
        log.info("updateHotel triggered with values: hotelOwnerId {}, hotelId: {}, dto: {}", hotelOwnerId, hotelId, dto);

        log.debug("Finding Hotel Owner by ID...");
        HotelOwner owner = findHotelOwnerById(hotelOwnerId);
        
        log.debug("Hotel Owner found! Finding Hotel By ID...");
        Hotel hotel = findHotelById(hotelId);

        if (!owner.getHotels().contains(hotel)) {
            log.warn("Hotel is not associated with this Hotel Owner!");
            throw new ActionNotAllowedException("Cannot update a hotel not owned by you.");
        }

        log.debug("Hotel found and associated with owner. Updating hotel details...");
        hotel.setName(dto.getName());
        hotel.setDescription(dto.getDescription());
        hotel.setAddress(addressConverter.toEntity(dto.getAddress()));
        hotel.setFacilities(dto.getFacilities().stream()
            .map(facilityConverter::mapStringToFacility)
            .collect(Collectors.toList()));

        log.debug("Updating hotel rooms...");
        updateRooms(hotel, HotelRoomType.SINGLE, dto.getNumberOfSingleRooms(), dto.getPriceOfSingleRooms());
        updateRooms(hotel, HotelRoomType.DOUBLE, dto.getNumberOfDoubleRooms(), dto.getPriceOfDoubleRooms());
        updateRooms(hotel, HotelRoomType.SUITE, dto.getNumberOfSuiteRooms(), dto.getPriceOfSuiteRooms());
        updateRooms(hotel, HotelRoomType.FAMILY, dto.getNumberOfFamilyRooms(), dto.getPriceOfFamilyRooms());
        updateRooms(hotel, HotelRoomType.DELUXE, dto.getNumberOfDeluxeRooms(), dto.getPriceOfDeluxeRooms());

        log.debug("Persisting updated hotel...");
        hotel = hotelRepo.save(hotel);

        log.info("Hotel updated successfully.");
        return hotelConverter.toDto(hotel);
    }

    public HotelRoomDTO updateHotelRoom(UUID hotelOwnerId, UUID hotelId, UUID hotelRoomId, HotelRoomDTO dto) {
        log.info("updateHotelRoom triggered with values: hotelOwnerId {}, hotelId: {}, hotelRoomId: {}, dto: {}", hotelRoomId, hotelId, hotelRoomId, dto);

        log.debug("Finding hotel owner by ID...");
        HotelOwner owner = findHotelOwnerById(hotelOwnerId);

        log.debug("Hotel owner found, finding Hotel by ID...");
        Hotel hotel = findHotelById(hotelId);

        log.debug("hotel found, checking if hotel is associated with owner");
        
        if(!owner.getHotels().contains(hotel)) {
            throw new ActionNotAllowedException("Cannot update hotel room, hotel not associated with owner...");
        }

        log.debug("Hotel associated with owner. Finding hotel room by ID");
        HotelRoom room = hotelRoomRepo
            .findById(hotelRoomId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel Room not found with the provided ID: " + hotelRoomId));
        
        log.debug("hotel room found, checking if room is associated with hotel...");
        
        if(!hotel.getRooms().contains(room)) {
            throw new ActionNotAllowedException("Room is not associated with hotel, can't update.");
        }

        log.debug("Room is associated with hotel, updating...");
        HotelRoom updatedRoom = roomConverter.toEntity(dto); 
        room.setPrice(updatedRoom.getPrice());
        room.setRoomType(updatedRoom.getRoomType());
        room.setCheckin(updatedRoom.getCheckin());
        room.setCheckout(updatedRoom.getCheckout());
        room.setAvailable(updatedRoom.isAvailable());
        
        log.debug("values updated, persisting...");
        owner = ownerRepo.save(owner);

        HotelRoom persistedRoom = owner
            .getHotels()
            .stream()
            .filter(hotelFilter -> hotelFilter.getHotelId().equals(hotelId))
            .findFirst()
            .flatMap(hotelFilter -> hotelFilter
                .getRooms()
                .stream()
                .filter(roomFilter -> roomFilter.getHotelRoomId().equals(hotelRoomId))
                .findFirst())
            .get();
            
        return roomConverter.toDto(persistedRoom);
    }

    private HotelOwner findHotelOwnerById(UUID id) {
        return ownerRepo
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Hotel Owner with ID " + id + " not found")
            );
    }

    private Hotel findHotelById(UUID id) {
        return hotelRepo
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Hotel not found with ID " + id + " not found")
            );
    }

    private void updateRooms(Hotel hotel, HotelRoomType roomType, int newRoomCount, double newPrice) {
        List<HotelRoom> existingRooms = hotel.getRooms().stream()
            .filter(room -> room.getRoomType() == roomType)
            .collect(Collectors.toList());
    
        int existingCount = existingRooms.size();
    
        // Remove extra rooms if new count is less
        if (newRoomCount < existingCount) {
            log.debug("Reducing {} rooms for room type {} to {}", existingCount - newRoomCount, roomType, newRoomCount);
            List<HotelRoom> roomsToRemove = existingRooms.subList(0, existingCount - newRoomCount);
            hotel.getRooms().removeAll(roomsToRemove);
            hotelRoomRepo.deleteAll(roomsToRemove);
        }
    
        // Add extra rooms if new count is more
        if (newRoomCount > existingCount) {
            log.debug("Adding {} new rooms for room type {} to match count {}", newRoomCount - existingCount, roomType, newRoomCount);
            for (int i = 0; i < newRoomCount - existingCount; i++) {
                HotelRoom newRoom = new HotelRoom();
                newRoom.setRoomType(roomType);
                newRoom.setPrice(newPrice);
                hotel.addHotelRoom(newRoom);
            }
        }
    
        // Update price for all rooms of this type
        log.debug("Updating price for {} rooms of type {} to {}", existingRooms.size(), roomType, newPrice);
        for (HotelRoom room : existingRooms) {
            room.setPrice(newPrice);
        }
    }
}
