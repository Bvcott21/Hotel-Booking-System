package com.bvcott.booking.mapping.service.booking;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bvcott.booking.dto.booking.BookingDTO;
import com.bvcott.booking.mapping.service.hotel.HotelMappingService;
import com.bvcott.booking.mapping.service.hotel.room.HotelRoomMappingService;
import com.bvcott.booking.model.booking.Booking;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class BookingMappingService {
	private final HotelMappingService hotelMappingService;
	private final HotelRoomMappingService roomMappingService;
	
	public BookingDTO toDto(Booking entity) {
		return BookingDTO
	            .builder()
	            .bookingId(entity.getBookingId())
	            .hotel(hotelMappingService.toDto(entity.getHotel()))
	            .rooms(entity
	                .getRooms()
	                .stream()
	                .map(roomMappingService::toDto)
	                .collect(Collectors.toList()))
	            .checkin(entity.getCheckin())
	            .checkout(entity.getCheckout())
	            .price(entity.getPrice())
	            .build();
	}
	
	
}
