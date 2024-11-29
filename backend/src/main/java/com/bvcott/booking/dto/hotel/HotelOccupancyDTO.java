package com.bvcott.booking.dto.hotel;

import java.util.List;
import java.util.UUID;

import com.bvcott.booking.dto.hotel.room.HotelRoomOccupancyDTO;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class HotelOccupancyDTO {
	private UUID hotelId;
	private String name;
	private List<HotelRoomOccupancyDTO> roomOccupancy;
}
