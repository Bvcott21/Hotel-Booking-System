package com.bvcott.booking.converter.hotel;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.hotel.HotelOccupancyDTO;
import com.bvcott.booking.dto.hotel.room.HotelRoomOccupancyDTO;
import com.bvcott.booking.mapping.service.hotel.HotelMappingService;
import com.bvcott.booking.model.hotel.Hotel;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor

public class HotelConverter {
    private final HotelMappingService hotelMappingService;
    
    public HotelOccupancyDTO toOccupancyDto(Hotel hotel, List<HotelRoomOccupancyDTO> roomOccupancy) {
    	return hotelMappingService.toOccupancyDto(hotel, roomOccupancy);
    }
}
