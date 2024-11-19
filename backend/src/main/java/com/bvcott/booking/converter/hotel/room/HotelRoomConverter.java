package com.bvcott.booking.converter.hotel.room;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.hotel.HotelRoomDTO;
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

}
