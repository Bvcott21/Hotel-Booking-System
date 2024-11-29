package com.bvcott.booking.service.user;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.hotel.HotelConverter;
import com.bvcott.booking.converter.hotel.room.HotelRoomConverter;
import com.bvcott.booking.dto.hotel.HotelOccupancyDTO;
import com.bvcott.booking.dto.hotel.room.HotelRoomOccupancyDTO;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.booking.BookingRepository;
import com.bvcott.booking.repository.hotel.HotelRepository;
import com.bvcott.booking.repository.user.HotelOwnerRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class HotelOwnerService {
	private static final Logger log = LoggerFactory.getLogger(HotelOwnerService.class);
	private final HotelRepository hotelRepo;
	private final HotelOwnerRepository ownerRepo;
    private final BookingRepository bookingRepo;
    private final HotelConverter hotelConverter;
    private final HotelRoomConverter roomConverter;

    public List<HotelOccupancyDTO> getHotelOccupancyAndBookings(UUID ownerId, LocalDate currentDate) {
    	log.info("getHotelOccupancyAndBookings triggered with values: ownerId {}, currentDate {}", ownerId, currentDate);
    	
    	log.debug("Fetching hotel owner by id...");
    	HotelOwner owner = ownerRepo
    		.findById(ownerId)
    		.orElseThrow(() -> new ResourceNotFoundException("Couldn't find hotel owner with id: " + ownerId));
    	
    	log.debug("Owner found, capturing hotels...");
    	List<Hotel> ownedHotels = owner.getHotels();
    	
    	log.debug("Fetching occupancy and future bookings for each hotel...");
    	return ownedHotels
    			.stream()
    			.map(hotel -> processHotelOccupancy(hotel, currentDate))
    			.collect(Collectors.toList());
    }
    
    private HotelRoomOccupancyDTO processRoomOccupancy(HotelRoom room, List<Booking> bookings, LocalDate currentDate) {
        // Determine if the room is currently occupied
        boolean isOccupied = bookings.stream()
            .anyMatch(booking -> !currentDate.isBefore(booking.getCheckin()) && !currentDate.isAfter(booking.getCheckout()));

        // Extract check-in and check-out dates from the first booking (if any)
        LocalDate checkin = bookings.isEmpty() ? null : bookings.get(0).getCheckin();
        LocalDate checkout = bookings.isEmpty() ? null : bookings.get(0).getCheckout();

        // Use mapping service to generate RoomOccupancyDTO
        return roomConverter.toOccupancyDTO(room, isOccupied, checkin, checkout);
    }
    
    private HotelOccupancyDTO processHotelOccupancy(Hotel hotel, LocalDate currentDate) {
        log.info("Processing occupancy for hotel: {}", hotel.getName());

        // Fetch bookings for all rooms in the hotel
        Map<HotelRoom, List<Booking>> roomBookingsMap = fetchBookingsForHotel(hotel, currentDate);

        // Process occupancy for each room
        List<HotelRoomOccupancyDTO> roomOccupancyDtos = hotel.getRooms().stream()
            .map(room -> processRoomOccupancy(room, roomBookingsMap.getOrDefault(room, List.of()), currentDate))
            .collect(Collectors.toList());

        // Convert to HotelOccupancyDTO using the HotelConverter
        return hotelConverter.toOccupancyDto(hotel, roomOccupancyDtos);
    }

	private Map<HotelRoom, List<Booking>> fetchBookingsForHotel(Hotel hotel, LocalDate currentDate) {
		log.debug("fetchBookingsForHotel helper method triggered with values: hotel {}, currentDate {}", hotel, currentDate);
		
		Map<HotelRoom, List<Booking>> roomBookingsMap = new HashMap<>();
		
		for (HotelRoom room : hotel.getRooms()) {
			List<Booking> bookings = bookingRepo.findBookingsForHotelWithinDateRange(hotel.getHotelId(), currentDate, LocalDate.now().plusYears(1));
			
			roomBookingsMap.put(room, bookings);
		}
		
		log.debug("Room bookings mapped!");
		return roomBookingsMap;
	}
}
