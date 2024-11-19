package com.bvcott.booking.mapping.service.hotel.room;

import org.springframework.stereotype.Service;

import com.bvcott.booking.dto.hotel.HotelRoomDTO;
import com.bvcott.booking.model.hotel.HotelRoom;
import com.bvcott.booking.model.hotel.HotelRoomType;

@Service
public class HotelRoomMappingService {
	public HotelRoomDTO toDto(HotelRoom entity) {
        return HotelRoomDTO.builder()
            .hotelRoomId(entity.getHotelRoomId())
            .roomType(entity.getRoomType().getHotelRoomType())
            .price(entity.getPrice())
            .isAvailable(entity.isAvailable())
            .checkin(entity.getCheckin())
            .checkout(entity.getCheckout())
            .build();
    }
	
	public HotelRoom toEntity(HotelRoomDTO dto) {
        HotelRoom entity = new HotelRoom();
        
        entity.setHotelRoomId(dto.getHotelRoomId());
        entity.setRoomType(mapStringToHotelRoomType(dto.getRoomType()));
        entity.setPrice(dto.getPrice());
        entity.setAvailable(dto.isAvailable());
        entity.setCheckin(dto.getCheckin());
        entity.setCheckout(dto.getCheckout());
        
        return entity;
    }

    private HotelRoomType mapStringToHotelRoomType(String hotelRoomType) {
        for (HotelRoomType roomType : HotelRoomType.values()) {
            if(roomType.getHotelRoomType().equalsIgnoreCase(hotelRoomType)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException("Invalid room type: " + hotelRoomType);
    }
}