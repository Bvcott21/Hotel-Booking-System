package com.bvcott.booking.converter.hotel.room;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.hotel.room.HotelRoomDTO;
import com.bvcott.booking.dto.hotel.room.HotelRoomOccupancyDTO;
import com.bvcott.booking.mapping.service.hotel.room.HotelRoomMappingService;
import com.bvcott.booking.model.hotel.HotelRoom;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor 
public class HotelRoomConverter {
    private final HotelRoomMappingService roomMappingService;

    public HotelRoomDTO toDto(HotelRoom entity) {
        return roomMappingService.toDto(entity);
    }

    public HotelRoom toEntity(HotelRoomDTO dto) {
        return roomMappingService.toEntity(dto);
    }
    
    public HotelRoomOccupancyDTO toOccupancyDTO(HotelRoom room, boolean isOccupied, LocalDate checkin, LocalDate checkout) {
    	return roomMappingService.toOccupancyDTO(room, isOccupied, checkin, checkout);
    }

}
